

#include "FirebaseESP8266.h"
#include "ESP8266WiFi.h"
#include "DHT.h"

//Define WiFi
const char *ssid = "erut";
const char *password = "apaaja21";

//Define trigger and echo digital pins
const int trigPin = 16; //D0
const int echoPin = 5; //D1

const int buzzerPin = 14; //D5
const int dhtPin = 4; //D2
const int pompaPin = 1; //TX
const int lampuPin = 13; //D7

#define DHTTYPE DHT11
DHT dht(dhtPin, DHTTYPE);

// The amount of time the ultrassonic wave will be travelling for
long duration = 0;
// Define the distance variable
double distance = 0;

FirebaseData firebaseData;

void setup()
{
    Serial.begin(9600);

    digitalWrite(lampuPin, LOW);
    digitalWrite(pompaPin, LOW);
    digitalWrite(buzzerPin, LOW);

    konekWifi();
    Firebase.begin("https://trialled-5df02-default-rtdb.firebaseio.com/", "yIiKQPQ5EGKbc6gwoG9w6q8wrhEqlOFuXW4b7VPT");
    //Firebase.begin("firebase host", "firebase auth database");

    pinMode(trigPin, OUTPUT);
    pinMode(echoPin, INPUT);
    pinMode(buzzerPin, OUTPUT);
    pinMode(pompaPin, OUTPUT);
    pinMode(lampuPin, OUTPUT);
    dht.begin();
}

void konekWifi()
{
    WiFi.begin(ssid, password);
    //memulai menghubungkan ke wifi router
    while (WiFi.status() != WL_CONNECTED)
    {
        delay(500);
        Serial.print("."); //status saat mengkoneksikan
    }
    Serial.println("Sukses terkoneksi wifi!");
    Serial.println("IP Address:"); //alamat ip lokal
    Serial.println(WiFi.localIP());
}

void loop()
{
    getLampu();
    
    getSecurityMaling();
    getSecurityApi();
}

void getLampu()
{
    if (Firebase.getString(firebaseData, "/status_lampu"))
    { //database field lampu
        if (firebaseData.dataType() == "string")
        {
            String FBStatusLampu = firebaseData.stringData();
            if (FBStatusLampu == "off")
            {
                Serial.println("Lampu OFF");
                digitalWrite(lampuPin, LOW);
            }
            else if (FBStatusLampu == "on")
            {
                Serial.println("Lampu ON");
                digitalWrite(lampuPin, HIGH);
            }
            else
            {
                Serial.println("Salah kode! isi dengan data ON/OFF");
            }
        }
    }
}

void getPompa()
{
    if (Firebase.getString(firebaseData, "/status_pompa"))
    { //misal database diberikan nama led1
        if (firebaseData.dataType() == "string")
        {
            String FBStatusPompa = firebaseData.stringData();
            if (FBStatusPompa == "off")
            {
                Serial.println("Pompa OFF");
                digitalWrite(pompaPin, LOW);
            }
            else if (FBStatusPompa == "on")
            {
                Serial.println("Pompa ON");
                digitalWrite(pompaPin, HIGH);
            }
            else
            {
                Serial.println("Salah kode! isi dengan data ON/OFF");
            }
        }
    }
}

void getSecurityMaling()
{
    
    if (Firebase.getString(firebaseData, "/securityMaling"))
    { 
        if (firebaseData.dataType() == "string")
        {
            String FStatusSecurityMaling = firebaseData.stringData();
            if (FStatusSecurityMaling == "on")
            {
                // Clear trigPin
                digitalWrite(trigPin, LOW);
                delayMicroseconds(2);

                // trigPin HIGH por 10ms
                digitalWrite(trigPin, HIGH);
                delayMicroseconds(10);
                digitalWrite(trigPin, LOW);
                //Reads echoPin, returns the travel time of the sound wave in ms
                duration = pulseIn(echoPin, HIGH);
                // Calculating the distance, in centimeters, using the formula described in the first section.
                distance = duration * 0.034 / 2;
                Serial.println("Fungsi Alarm Maling Hidup!");
                // Prints the distance value to the serial monitor
                Serial.print("Distance: ");
                Serial.println(distance);
                Firebase.setFloat(firebaseData, "/distance", distance);

                //Logika Alarm
                if (distance > 10)
                {
                    //alrm hidup
                    Serial.println("Alarm Maling Hidup!");
                    
                    Firebase.setString(firebaseData, "/status_buzzer", "on");
                    Firebase.setString(firebaseData, "/maling", "on");
                    //buzer idup
                    digitalWrite(buzzerPin, HIGH);
                    delay(1000);
                    digitalWrite(buzzerPin, LOW);
                    delay(50);
                    
                }
                else
                {
                    //alrm mati
                    Serial.println("Alarm Maling Mati!");
                    Firebase.setString(firebaseData, "/status_buzzer", "off");
                    Firebase.setString(firebaseData, "/maling", "off");

                    //buzer mati
                    digitalWrite(buzzerPin, LOW);
                }
            }
            else if (FStatusSecurityMaling == "off")
            {
                //matikan alrm
                Firebase.setString(firebaseData, "/maling", "off");
                Serial.println("Fungsi Alarm Maling Mati!");
            }
            else
            {
                Serial.println("Salah kode! isi dengan data ON/OFF");
            }
        }
    }
}

void getSecurityApi()
{
    float h = dht.readHumidity();
    // Read temperature as Celsius (the default)
    float t = dht.readTemperature();

    //Firebase.setFloat(distance, "/status_distance");
    if (Firebase.getString(firebaseData, "/securityApi"))
    { //misal database diberikan nama led1
        if (firebaseData.dataType() == "string")
        {
            String FStatusSecurityApi = firebaseData.stringData();
            if (FStatusSecurityApi == "on")
            {
                Serial.print(F("Humidity: "));
                Serial.print(h);
                Serial.print(F("%  Temperature: "));
                Serial.print(t);
                Serial.print(F("C  ,"));
                Serial.println("Fungsi Alarm Api Hidup!");
                Firebase.setFloat(firebaseData, "/dht/humidity", h);
                Firebase.setFloat(firebaseData, "/dht/temperature", t);

                //Logika Alarm
                if (t > 30)
                {
                    //alrm hidup
                    Serial.println("Alarm Api Hidup!");
                    //buzer idup
                    digitalWrite(buzzerPin, HIGH);
                    delay(1000);
                    digitalWrite(buzzerPin, LOW);
                    delay(50);
                    //pompa Idup
                    Firebase.setString(firebaseData, "/api", "on");
                    Firebase.setString(firebaseData, "/status_pompa", "on");
                    Firebase.setString(firebaseData, "/status_buzzer", "on");
                }
                else
                {
                    //alrm mati
                    Serial.println("Alarm Api Mati!");
                    //pompa Mati
                    Firebase.setString(firebaseData, "/api", "off");
                    Firebase.setString(firebaseData, "/status_pompa", "off");
                    Firebase.setString(firebaseData, "/status_buzzer", "off");
                    //buzer mati
                    digitalWrite(buzzerPin, LOW);
                }
            }
            else if (FStatusSecurityApi == "off")
            {
                //matikan alrm
                
                Firebase.setString(firebaseData, "/api", "off");
                Firebase.setString(firebaseData, "/status_pompa", "off");
                Serial.println("Fungsi Alarm Api Mati!");
            }
            else
            {
                Serial.println("Salah kode! isi dengan data ON/OFF");
            }
        }
    }
}
