# Ders 1 — Bankacılık Temel Kavramları

## Kısa anlatım
Banka iki temel iş yapar: mevduat toplar (parası olandan), kredi verir (paraya ihtiyacı olana).
Aradaki faiz farkı = net faiz marjı = bankanın kazancı.

## Dört yapı taşı
| Kavram | Kodda | Ne |
|---|---|---|
| Müşteri | `Customer` | Bankayla ilişkisi olan kişi/şirket |
| Hesap | `Account` | Paranın durduğu yer |
| Ürün | `LoanProduct` | Bankanın sattığı şey |
| İşlem | `AccountTransaction` | Para hareketi kaydı |

## Akış
```
Müşteri → Hesap açar → Para yatırır/çeker → Kredi başvurusu → Değerlendirme → Kullandırım → Taksit öder
```

## Kritik: bilanço bakışı
- **Kredi = bankanın ALACAĞI** (aktif) — para dışarıda, geri gelmeyebilir → RİSK
- **Mevduat = bankanın BORCU** (pasif)
- "Risk" kelimesi bankacılıkta = müşterinin bankaya toplam borcu. "Riski 2M TL" = "2M TL borçlu".

## 🎯 Mülakat
**S: Müşteri, hesap ve kredi ilişkisini açıklayın.**
C: Müşteri kök varlıktır; birden fazla hesabı ve kredisi olabilir (1:N). Kullandırımda para bir
hesaba geçer. Kredi bankanın aktifinde bir alacaktır; bu yüzden müşteri bazında toplam risk izlenir.
