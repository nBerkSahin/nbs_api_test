# Swagger Petstore API Test Project (ENG)
This project was developed using Java, RestAssured, and TestNG to automatically test various endpoints of the [Swagger Petstore](https://petstore.swagger.io/) API.
## Features
- Tests for adding, updating, deleting, and querying pets
- Updating pets with form data (application/x-www-form-urlencoded)
- Verification of API responses and error handling
- Modular and readable test structure
## Requirements
- Java 17+ (project compatible with Java 24)
- Maven 3.6+
- IntelliJ IDEA or a similar IDE
## Installation
1. Clone the repository: `git clone https://github.com/nBerkSahin/nbs_api_test.git`
2. Enter the project directory: `cd ins_api_test`
3. Install the necessary dependencies: `mvn clean install`
## Running Tests
To run all API tests:
`mvn test` or `mvnd test`
## Structure
- `src/test/java/tests` : Test classes (API tests)
- `src/test/java/endpoints/` : API endpoint constants
- `src/test/java/utils/` : Utility methods (request sending, response validation)
- `src/test/java/models/` : Data models (Pet, Category, Tag, etc.)
## Notes
- Tests cover CRUD and form-data operations on the Swagger Petstore API.
- Responses and error messages are automatically validated in case of errors.

--------------------------------------------------------------------------------------------------------


# Swagger Petstore API Test Projesi (TR)
Bu proje, [Swagger Petstore](https://petstore.swagger.io/) API'sinin çeşitli uç noktalarını otomatik olarak test etmek için Java, RestAssured ve TestNG kullanılarak geliştirilmiştir.
## Özellikler
- Pet ekleme, güncelleme, silme ve sorgulama testleri
- Form-data ile pet güncelleme (application/x-www-form-urlencoded)
- API yanıtlarının doğrulanması ve hata yönetimi
- Modüler ve okunabilir test yapısı
## Gereksinimler
- Java 17+ (proje Java 24 ile uyumlu)
- Maven 3.6+
- IntelliJ IDEA veya benzeri bir IDE
## Kurulum
1. Depoyu klonlayın: `git clone https://github.com/nBerkSahin/nbs_api_test.git`
2. Proje dizinine girin: `cd ins_api_test`
3. Gerekli bağımlılıkları yükleyin: `mvn clean install`
## Testleri Çalıştırma
Tüm API testlerini çalıştırmak için:
`mvn test` veya `mvnd test`
## Yapı
- `src/test/java/tests` : Test sınıfları (API testleri)
- `src/test/java/endpoints/` : API uç noktaları sabitleri
- `src/test/java/utils/` : Yardımcı metotlar (istek gönderme, yanıt doğrulama)
- `src/test/java/models/` : Veri modelleri (Pet, Category, Tag vb.)
## Notlar
- Testler Swagger Petstore API üzerinde CRUD ve form-data işlemlerini kapsamaktadır.
- Hatalı durumlarda yanıtlar ve hata mesajları otomatik olarak doğrulanır.
