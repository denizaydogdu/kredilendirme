# Kredilendirme

Türk Ticaret Bankası'nın kurumsal kredi ürün setini modelleyen bir kredilendirme sistemi.
Nakit ve gayrinakdi kredi ürünleri, kredi başvuru süreci, limit tahsis, kullandırım ve
ödeme planı üretimi uçtan uca modellenmiştir.

> **Not:** Bu bir öğrenme ve gösterim projesidir; gerçek bir banka sistemi değildir.
> Ürün tanımları ve iş kuralları kamuya açık banka ürün dokümanlarından modellenmiştir.
> Gerçek müşteri verisi veya banka entegrasyonu içermez.

## Teknoloji

| Katman | Teknoloji |
|---|---|
| Dil | Java 8 |
| Framework | Spring Boot 2.7 |
| Veri erişimi | Spring Data JPA / Hibernate |
| Veritabanı | PostgreSQL 14 |
| Şema yönetimi | Flyway |
| Arayüz | Thymeleaf + Bootstrap 5 |
| Mesajlaşma | Apache Kafka |
| Dayanıklılık | Resilience4j |
| Build | Maven (multi-module) |
| Test | JUnit 5, Mockito, AssertJ |

## Modüller

| Modül | Port | Sorumluluk |
|---|---|---|
| `kredilendirme-common` | — | Servisler arası ortak sözleşmeler |
| `kredi-service` | 8081 | Müşteri, ürün, başvuru, teminat, limit, ödeme planı |
| `disbursement-service` | 8082 | Kullandırım ve hesap hareketleri |
| `api-gateway` | 8080 | Yönlendirme ve dış dünyaya tek giriş noktası |

## Kurulum

```bash
createdb kredi_db
mvn clean install
mvn -pl kredi-service spring-boot:run
```

Gereksinimler: JDK 8, Maven 3.6+, PostgreSQL 14+

## Dokümantasyon

| Doküman | İçerik |
|---|---|
| `docs/BANKACILIK-SOZLUGU.md` | Bankacılık terimleri ve İngilizce karşılıkları |
| `docs/adr/` | Mimari karar kayıtları |
| `docs/SAGA.md` | Kullandırım saga akışı ve hata senaryoları |
| `docs/ER-DIYAGRAM.md` | Veri modeli |
