# Ders 9 — application.yml, Profiller ve Kritik JPA Ayarları

## Profiller
```
application.yml       ← ortak
application-dev.yml   ← geliştirme (show-sql açık)
application-prod.yml  ← canlı
```
Aktif profil ortak dosyayı ezer. Şifre asla yml'a yazılmaz:
`password: ${DB_PASSWORD:}` → ortam değişkeninden gelir.

## Üç kritik ayar

### 1) `ddl-auto: validate`
| Değer | Ne yapar |
|---|---|
| create/create-drop | siler-yaratır — sadece test |
| update | şemayı değiştirmeye çalışır — CANLIDA ASLA (sessiz veri kaybı) |
| **validate** | sadece kontrol — şema Flyway'in, uymazsa uygulama açılmaz ✅ |

### 2) `open-in-view: false` (en çok sorulan)
`true` (varsayılan): EntityManager HTTP isteği bitene kadar açık → view'da lazy dokunuş
**sessizce sorgu atar** → gizli N+1. `false`: oturum Service'te kapanır; dışarıda lazy dokunuş
`LazyInitializationException` → hata erken ve gürültülü görünür, veri Service'te bilinçli yüklenir.

### 3) Flyway
- Dosya adı: `V1__aciklama.sql` (ÇİFT alt çizgi).
- Uygulanan migration **asla değiştirilmez** (checksum kontrolü); değişiklik = yeni V dosyası.
- `flyway_schema_history` tablosu geçmişi tutar; uygulanmış sürümler atlanır.

## 🎯 Mülakat
**S: open-in-view neden kapatılır?**
C: Persistence context'i isteğin sonuna kadar açık tutar; view katmanında farkında olmadan lazy
yükleme ve N+1'e yol açar. Kapatınca veriyi servis katmanında `join fetch`/`@EntityGraph` ile
bilinçli yüklerim; hata olursa `LazyInitializationException` ile erken görürüm.
