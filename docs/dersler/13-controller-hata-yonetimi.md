# Ders 13 — Controller ve Global Hata Yönetimi

## İstek yolculuğu
```
DispatcherServlet → Controller (@Valid burada) → Service → Repository → DB
                  ← CustomerResponse ← Jackson ← 201 Created
```

## Anotasyon sözlüğü
- `@RestController` = `@Controller` + `@ResponseBody` (JSON döner; Thymeleaf'te `@Controller` + view adı).
- `@PathVariable` → URL parçası (`/customers/{id}`) — kaynağın kimliği.
- `@RequestParam` → query string (`?title=&page=`) — filtre/sayfalama.
- `@RequestBody` → gövdedeki JSON → DTO.

## URL tasarımı
`/api/v1/customers` — isim (çoğul), fiil YOK; fiil HTTP metodunda. `v1` = versiyonlama.

## Status kod haritamız
| Kod | Durum | Kaynak |
|---|---|---|
| 201 + Location | oluşturuldu | create |
| 204 | yapıldı, gövde yok | deactivate |
| 400 + fieldErrors | biçim hatası | `MethodArgumentNotValidException` |
| 404 | kayıt yok | `ResourceNotFoundException` |
| 422 | iş kuralı reddi | `BusinessException` |
| 500 | bizim bug | catch-all |

**400 vs 422**: "isteği anlamadım" vs "anladım ama kurallar reddediyor".
**401 vs 403**: "kim olduğunu bilmiyorum" vs "kimsin biliyorum, yetkin yok".

## @RestControllerAdvice
Tüm controller'ları saran merkezi handler. **En spesifik exception tipi kazanır**
(ResourceNotFound, BusinessException'dan türese de 404 handler'ı seçilir).
Catch-all: stack trace **içeride loglanır**, istemciye **asla dönmez** (bilgi sızıntısı).

## İncelikler
- 201'de `Location: /api/v1/customers/1` header'ı (ServletUriComponentsBuilder).
- DELETE fiziksel silmez → **soft delete** (bankada kayıt silinmez: denetim + yasal saklama).
- `Collectors.toMap(key, value, mergeFn)` — mergeFn yazılmazsa duplicate key'de exception (tuzak).

## 🎯 Mülakat
**S: Hata yönetimini nasıl yaparsınız?**
C: Controller'da try-catch değil, `@RestControllerAdvice` ile merkezi handler. Exception
hiyerarşisi HTTP'ye maplenir: 404/422/400+alan haritası. Beklenmeyenlerde stack trace loglanır
ama istemciye genel mesaj döner.
