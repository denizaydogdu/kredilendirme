# Ders 3 — Kredi Ekranı Teknik Olarak Nasıl Çalışır?

## İstek zinciri
```
TARAYICI  → POST /api/loan-applications {customerId, amount, term}
CONTROLLER → JSON'u DTO'ya çevirir, @Valid, iş kuralı YOK
SERVICE    → iş kuralları + @Transactional
REPOSITORY → sadece veri erişimi, iş kuralı YOK
DATABASE   → INSERT
← 201 Created + başvuru no
```

## Katman sorumlulukları (SOLID/SRP)
| Katman | Görev | ASLA olmaz |
|---|---|---|
| Controller | HTTP karşıla/dön | iş kuralı, SQL |
| Service | iş kuralları, transaction | HTTP nesneleri, SQL |
| Repository | veri erişimi | iş kuralı |
| Entity | tablonun Java karşılığı | HTTP/JSON mantığı |

**Neden:** Yarın aynı işlemi başka kanaldan (batch, başka banka API'si) çağırırlarsa yeni
Controller yazılır, Service aynen kullanılır. İş kuralı Controller'da olsaydı kopyalanırdı.

## Ekran = API tasarımı
"Müşteri Unvanı" alanı müşteri no girilince otomatik dolar → arkada ayrı istek:
`GET /api/customers/10001`. Ekrandaki her otomatik alan bir endpoint demektir.

## 🎯 Mülakat
**S: Kredi ekranının backend mimarisini nasıl tasarlardınız?**
C: Katmanlı mimari: Controller doğrulama, Service iş kuralları + `@Transactional`,
Repository veri erişimi. Dışarıya Entity değil DTO dönerim. Katmanları teknik değil iş alanı
bazında paketlerim; paket sınırları ileride servis sınırı olur.
