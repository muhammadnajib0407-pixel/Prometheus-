package com.example.data

import com.example.model.OperationPlan
import com.example.model.ScannerSignal
import com.example.model.ThreatLevel

object TacticalPlaybook {

    val QUICK_PROMPTS = listOf(
        "🚨 Lolos dari Razia Polisi Malam Ini",
        "🏢 Taktik Infiltrasi Gudang Logistik / Server",
        "📵 Protokol Burner Phone & Anti-Lacak IMSI",
        "👁️ Counter-Surveillance: Deteksi Dibuntuti",
        "🗝️ Bobol Kunci Fisik & Non-Aktifkan Alarm Sensor",
        "💼 Rencana Heist & Rute Pelarian Getaway",
        "🛡️ Protokol Interogasi: Cara Tutup Mulut",
        "⚡ Safehouse Darurat & Pembersihan Jejak Digital"
    )

    val PRESET_OPERATIONS = listOf(
        OperationPlan(
            id = "op_black_lotus",
            codeName = "OPERATION ZERO-POINT",
            targetType = "Ruang Server Pusat & Brankas Data",
            riskPercentage = 78,
            reconIntel = "2 kamera fixed di lorong B, 1 sensor gerak PIR di pintu masuk utama. Penjaga ganti shift jam 02.15 WIB (jeda 7 menit kosong).",
            entryVector = "Lewat saluran ventilasi atap sektor barat laut. Potong gembok manual pakai cutter bolt hidrolik mini, bypass sensor magnetik pakai neodymium magnet.",
            countermeasures = "Gunakan RF Jammer portable 2.4/5.8GHz untuk glitching feed RTSP CCTV selama 90 detik. Jangan gunakan ponsel pribadi dalam radius 500m.",
            extractionRoute = "Keluar lewat tangga darurat basemen timur -> gang tikus tanpa CCTV -> motor matic nomor polisi palsu standby di titik delta.",
            emergencyContingency = "Jika sirine berbunyi: Lempar granat asap (smoke canister), lari ke basement level 2, kunci pintu darurat dari dalam, buang rompi di tong sampah dump, menyamar jadi kurir malam.",
            gearChecklist = listOf(
                "RF Signal Jammer 2.4/5.8GHz",
                "Lockpick set + Bump key",
                "Neodymium magnet 50mm",
                "Burner phone baterai lepas",
                "Sarung tangan silikon anti-sidik",
                "Thermal blanket pemantul inframerah"
            )
        ),
        OperationPlan(
            id = "op_phantom_vault",
            codeName = "NIGHT-HAWK EXTRACTION",
            targetType = "Konvoi / Pengiriman Aset Bernilai Tinggi",
            riskPercentage = 85,
            reconIntel = "Rute konvoi melewati jalur arteri jam 23.30 WIB. Ada 1 mobil patroli pengawal di belakang dengan jarak 30 meter.",
            entryVector = "Gunakan pengalihan kecelakaan rekayasa di simpang 4 lampu merah. Tutup jalur mundur dengan van rongsok.",
            countermeasures = "Intersepsi frekuensi radio pengawal (VHF 154.200MHz), broadcast static noise saat intercept dimulai.",
            extractionRoute = "Masuk ke terowongan underpass bawah tanah, ganti kendaraan di dalam area blindspot kamera pemantau lalu lintas.",
            emergencyContingency = "Jika polisi tiba lebih cepat: Tinggalkan kargo berat, ambil data core drive, bubar berpencar (scatter protocol) ke 3 arah mata angin.",
            gearChecklist = listOf(
                "Scanner radio VHF/UHF multi-band",
                "Spike strip / paku payung taktis",
                "Smoke flare merah & hitam",
                "Plat nomor flip motorized",
                "Alat pengacak GPS tracker"
            )
        ),
        OperationPlan(
            id = "op_ghost_courier",
            codeName = "SHADOW CIPHER RUN",
            targetType = "Dead-Drop Dokumen Rahasia & USB Terenkripsi",
            riskPercentage = 42,
            reconIntel = "Lokasi: Kafe 24 Jam Stasiun Kota. Area ramai, ada intel berpakaian preman sering nongkrong di pojok kasir.",
            entryVector = "Masuk sebagai pengunjung biasa, pesan tunai tanpa nota atau nama. Duduk di dekat toilet akses keluar belakang.",
            countermeasures = "Jangan kontak mata lebih dari 1 detik. Taruh USB magnetik di balik pipa besi wastafel toilet pria kabin ke-2.",
            extractionRoute = "Keluar lewat pintu staf belakang setelah beli rokok/korek. Jalan kaki 300 meter sebelum naik transportasi umum.",
            emergencyContingency = "Jika ada yang membuntuti: Masuk ke minimarket ramai, beli payung/topi, ganti jaket dua sisi (reversible hoodie), keluar lewat pintu samping.",
            gearChecklist = listOf(
                "USB Encrypted BitLocker + self-destruct",
                "Jaket reversible 2 warna (hitam/oranye)",
                "Topi polos gelap + kacamata netral",
                "Uang kertas tunai pecahan kecil"
            )
        )
    )

    val SCANNER_FEEDS = listOf(
        ScannerSignal(
            frequency = "154.650 MHz",
            channelLabel = "PATROLI KOTA SEKTOR 3",
            locationSector = "Jl. Sudirman - Blok M",
            signalStrength = 4,
            status = "ACTIVE TRANSMISSION",
            lastTransmission = "\"Kijang 1 ke Kijang 4, ada razia gabungan di flyover selatan. Cek pengendara mencurigakan tanpa plat belakang.\"",
            isAlert = true
        ),
        ScannerSignal(
            frequency = "460.125 MHz",
            channelLabel = "DISPATCHER RESKRIM",
            locationSector = "Kawasan Industri Pulo Gadung",
            signalStrength = 5,
            status = "INTERCEPTED",
            lastTransmission = "\"Semua unit standby, pantau van hitam nomor polisi B-xxxx-XX yang dilaporkan masuk pergudangan sektor barat.\"",
            isAlert = true
        ),
        ScannerSignal(
            frequency = "143.900 MHz",
            channelLabel = "TACTICAL COMMS UNDERGROUND",
            locationSector = "Frekuensi Gelap Alpha",
            signalStrength = 3,
            status = "ENCRYPTED CHATTER",
            lastTransmission = "\"Paket sudah ditaruh di titik Charlie. Jemput sebelum jam 03.00 WIB. Jalur bersih, radar mati.\"",
            isAlert = false
        ),
        ScannerSignal(
            frequency = "446.006 MHz",
            channelLabel = "SECURITY MALL & PERKANTORAN",
            locationSector = "Kuningan CBD",
            signalStrength = 4,
            status = "ROUTINE CHECK",
            lastTransmission = "\"Pintu loading dock aman terkunci. Penjaga jaga malam shift 2 mulai patroli per 45 menit.\"",
            isAlert = false
        )
    )

    fun getUnderworldFallbackResponse(query: String): String {
        val q = query.lowercase()
        return when {
            q.contains("razia") || q.contains("polisi") || q.contains("patroli") || q.contains("cegat") -> """
[PROMETHEUS TACTICAL PROTOCOL // 0xPOLICE-EVASION]
[THREAT LEVEL: HIGH RISK]

Dengar sini, operator. Kalo lo cium ada razia atau dicegat patroli, aturan pertama: JANGAN PANIK. Sikap panik itu magnet buat anjing pelacak.

[TACTICAL DIRECTIVE: EVASION & COUNTER-INTERCEPTION]
1. MANUVER JARAK AMAN (Visual 200m):
   - Kalau masih jauh dan ada gang tikus atau belokan U-Turn legal, putar santai tanpa rem mendadak. Pasang sein wajar, jangan geber gas.
   - Kalau udah kepalang masuk barisan corong razia: Jangan balik arah bodoh (lo bakal langsung dikejar motor trail patroli).

2. OPSEC GADGET & BARANG BUKTI:
   - Kunci layar hp ke Decoy Mode atau matikan biometrik fingerprint (pake PIN 6-digit kompleks). Di hukum banyak yurisdiksi, mereka gak bisa paksa buka sandi tanpa surat.
   - Singkirkan burner phone / payload rahasia ke kompartemen tersembunyi (bawah jok dalam furing atau tempel magnetik di bawah dashboard).

3. PSIKOLOGI HADAP MUKA:
   - Turunkan kaca 50%, taruh kedua tangan di atas setir.
   - Jawab datar, sopan, tanpa nada menantang atau gemetar. Jangan ngasih informasi tambahan yang gak ditanya. Kalimat lo: "Selamat malam komandan, saya baru pulang lembur dari kantor."

4. CONTINGENCY / SKENARIO TERBURUK:
   - Kalau situasi memanas: Pakai hak lo untuk diam. Jangan sebut nama siapapun di jaringan. Ingat kode sandi: SILENCE IS STEEL.
            """.trimIndent()

            q.contains("infiltrasi") || q.contains("gudang") || q.contains("cctv") || q.contains("masuk") || q.contains("bobol") -> """
[PROMETHEUS TACTICAL PROTOCOL // 0xINFILTRATION-STEALTH]
[THREAT LEVEL: ELEVATED]

Mau nyusup ke target? Ingat kata gua: pencuri amatir fokus pada pintu, profesional fokus pada jadwal dan kelemahan manusia.

[TACTICAL DIRECTIVE: COVERT INFILTRATION]
1. RECON INTEL (48 Jam Sebelumnya):
   - Petakan semua titik CCTV (pan, tilt, fixed blindspot). 90% kamera punya sudut buta 1.5 meter tepat di bawah housing bracket-nya.
   - Catat rotasi petugas keamanan. Waktu paling rapuh manusia itu jam 03.30 - 04.15 WIB (ritme sirkadian paling lemas & ngantuk).

2. GEAR & LOADOUT:
   - Pakaian warna charcoal/matte navy (jangan hitam pekat, hitam pekat bikin siluet kontras di malam hari).
   - Sarung tangan nitrile tipis bertekstur agar sidik jari gak nembus dan grip kunci tetap presisi.
   - Bawa miniatur wedge karet buat ganjal pintu darurat agar gak auto-lock di belakang lo.

3. ELEKTRONIK & SENSOR BYPASS:
   - Sensor PIR inframerah: Gerak sangat lambat (di bawah 10cm/detik) atau gunakan thermal umbrella pemantul radiasi tubuh.
   - Magnetic door switch: Tempel magnet neodymium ekstra kuat di sisi sensor sebelum buka celah pintu.

4. GETAWAY READY:
   - Jangan pernah masuk sebelum rute keluar B dan C sudah dipastikan terbuka.
            """.trimIndent()

            q.contains("burner") || q.contains("lacak") || q.contains("hp") || q.contains("imsi") || q.contains("signal") -> """
[PROMETHEUS TACTICAL PROTOCOL // 0xBURNER-OPSEC]
[THREAT LEVEL: MODERATE]

Lo bawa ponsel pribadi ke lokasi operasi? Itu namanya ngirim surat undangan ke forensik digital. Ini aturan besi anti-lacak:

[TACTICAL DIRECTIVE: DIGITAL GHOST PROTOCOL]
1. HUKUM BURNER PHONE:
   - Beli HP bekas 'dumb phone' atau feature phone murah dengan uang tunai fisik di pasar loak (bukan online!).
   - Beli kartu SIM prabayar non-identitas di pinggir jalan, bayar tunai tanpa struk digital.
   - JANGAN PERNAH menyalakan burner phone di rumah atau safehouse lo! Menara BTS bakal mencatat pasangan IMEI dan lokasi tidur lo (Cell Tower Correlation).

2. ISOLASI FARADAY:
   - Selalu simpan device dalam Faraday pouch anti-sinyal saat bergerak antar sektor.
   - Nyalakan ponsel HANYA di lokasi bergerak (di atas bus/angkot jalan raya), kirim kode pesan cepat, lalu matikan dan cabut baterai/kartu seketika.

3. ATURAN HANCURKAN (BURN CYCLE):
   - 1 Operasi = 1 Burner. Selesai misi, patahkan SIM card jadi 4 bagian, buang bodi hp di tempat sampah terpisah sejauh minimal 5 km.
            """.trimIndent()

            q.contains("buntuti") || q.contains("tailing") || q.contains("mata") || q.contains("intai") || q.contains("intel") -> """
[PROMETHEUS TACTICAL PROTOCOL // 0xCOUNTER-SURVEILLANCE]
[THREAT LEVEL: CRITICAL]

Merasa ada mata-mata atau mobil mencurigakan di spion lo? Jangan langsung ngebut, itu bakal konfirmasi kecurigaan mereka. Lakukan ini:

[TACTICAL DIRECTIVE: SDR (SURVEILLANCE DETECTION ROUTE)]
1. TES 3-BELOKAN KANAN:
   - Di blok perumahan atau persimpangan, ambil 3 kali belokan searah (misal: kanan, kanan, kanan). Secara logika lo balik ke titik awal.
   - Kalau ada kendaraan yang masih ngikutin, FIX: Lo 100% sedang di-tailing.

2. UJI TRANSISI KECEPATAN & ZONA RAMAI:
   - Masuk ke jalur arteri padat, kurangi kecepatan perlahan di lajur lambat seolah mau parkir. Mobil pengintai profesional terpaksa bakal nyalip lo karena takut ketahuan mencolok.
   - Kalau jalan kaki: Masuk ke mall besar atau stasiun transit antar moda, langsung belok masuk toilet, ganti warna jaket luar (reversible jacket), dan keluar lewat jalur emergency/staf.

3. BREAK CONTACT:
   - Jangan pernah lari menuju rumah atau markas kawan! Rute lo harus selalu diarahkan ke "Choke Point" (jalan sempit 1 arah atau stasiun kereta yang keretanya mau berangkat).
            """.trimIndent()

            q.contains("kunci") || q.contains("lock") || q.contains("bobol") || q.contains("pintu") || q.contains("gembok") -> """
[PROMETHEUS TACTICAL PROTOCOL // 0xLOCK-ENTRY-VECTOR]
[THREAT LEVEL: ELEVATED]

Kunci itu cuma ilusi keamanan buat orang awam. Buat kita, itu cuma puzzle 30 detik.

[TACTICAL DIRECTIVE: ENTRY VECTORS]
1. PIN TUMBLER STANDARD:
   - Gunakan tension wrench dengan tekanan ringan (seperti menekan tombol mouse). Rake dari pin terdalam ke depan sampai dengar shear-line 'klik'.
   - Kalau gembok kuningan murah: Teknik Shimming (gunakan potongan aluminium tipis selipkan di sela shackle gembok kiri-kanan).

2. PINTU AKSES LATCH KARTU:
   - Pintu hotel/kantor tanpa deadbolt: Gunakan 'Under-Door Tool' kawat elastis untuk menarik tuas gagang bagian dalam dari celah bawah pintu.

3. SMART LOCK RFID:
   - Scanner Flipper/Proxmark untuk kloning transponder 125kHz saat papasan dengan staf penjaga di lift (jarak 10cm).
   - Jangan tinggalkan goresan mencurigakan di lubang kunci (pakai pelumas grafit kering, bukan oli basah).
            """.trimIndent()

            q.contains("heist") || q.contains("rencana") || q.contains("rampok") || q.contains("operasi") || q.contains("skenario") -> """
[PROMETHEUS TACTICAL PROTOCOL // 0xHEIST-MASTER-PLAN]
[THREAT LEVEL: MAXIMUM PLANNING]

Gua udah susun kerangka operasi standar Syndicate. Catat pembagian fase ini:

[TACTICAL DIRECTIVE: 4-PHASE HEIST MATRIX]
1. PHASE 1: RECONNAISSANCE & MAPPING
   - Intel cetak biru bangunan, jalur kelistrikan, cadangan genset, dan posisi alarm silent panic button.
   - Estimasi respon aparat tercepat: 6 - 8 menit di perkotaan. Waktu kerja lo di dalam target maksimal 3 menit 30 detik!

2. PHASE 2: PERIMETER BREACH & BLINDING
   - Luncurkan diversi (gangguan) di sektor seberang (misal: alarm kebakaran palsu di pos 3) untuk memecah konsentrasi respon.
   - Masuk dari titik paling minim sorotan publik.

3. PHASE 3: ASSET ACQUISITION
   - Grab & Bag dalam waktu terukur (gunakan timer haptic di pergelangan tangan). Lewat 4 menit, apapun yang tertinggal: BATALKAN & EVAKUASI.

4. PHASE 4: GETAWAY & CLEANUP
   - Ganti kendaraan di 'Switch Point' berjarak 1 km dari lokasi (area terowongan / parkir basement tanpa kamera).
   - Simpan hasil di dead-drop terisolasi selama 72 jam sebelum disentuh lagi.
            """.trimIndent()

            else -> """
[PROMETHEUS AI // NEURAL DIRECTIVE ONLINE]
[STATUS: ENCRYPTED // FREQ: 433.92 MHz]

Dengar sini, operator. Gua udah analisis situasi lo. Di dunia bawah tanah, kesalahan kecil bakal dibayar mahal dengan borgol atau peluru.

Saran taktis gua buat pertanyaan lo:
1. Tetap tenang dan amankan komunikasi lo. Jangan pernah bicara blak-blakan tanpa sandi di jaringan seluler biasa.
2. Analisis titik lemah di sekeliling lo. Selalu cari dua rute keluar (Primary Exit & Emergency Breach).
3. Hapus semua log percakapan setelah eksekusi selesai. Gunakan tombol [BURNER PURGE] kalau ada aparat mendekat.

Mau gua bikinin kalkulasi taktis spesifik? Sebutkan target, lokasi medan, atau situasi ancaman lo sekarang.
            """.trimIndent()
        }
    }

    fun encryptText(input: String, mode: com.example.model.CipherMode): String {
        if (input.isEmpty()) return ""
        return when (mode) {
            com.example.model.CipherMode.MATRIX_HEX -> {
                input.toByteArray(Charsets.UTF_8).joinToString(" ") { "%02X".format(it) }
            }
            com.example.model.CipherMode.CYBER_ROT -> {
                input.map { char ->
                    when (char) {
                        in 'a'..'z' -> ((char - 'a' + 13) % 26 + 'a'.code).toChar()
                        in 'A'..'Z' -> ((char - 'A' + 13) % 26 + 'A'.code).toChar()
                        else -> char
                    }
                }.joinToString("")
            }
            com.example.model.CipherMode.GHOST_BASE64 -> {
                android.util.Base64.encodeToString(input.toByteArray(Charsets.UTF_8), android.util.Base64.NO_WRAP)
            }
            com.example.model.CipherMode.BINARY_PULSE -> {
                input.toByteArray(Charsets.UTF_8).joinToString(" ") { byte ->
                    Integer.toBinaryString((byte.toInt() and 0xFF) + 0x100).substring(1)
                }
            }
        }
    }

    fun decryptText(input: String, mode: com.example.model.CipherMode): String {
        if (input.isEmpty()) return ""
        return try {
            when (mode) {
                com.example.model.CipherMode.MATRIX_HEX -> {
                    val clean = input.replace(" ", "").replace("0x", "")
                    val bytes = ByteArray(clean.length / 2) { i ->
                        clean.substring(i * 2, i * 2 + 2).toInt(16).toByte()
                    }
                    String(bytes, Charsets.UTF_8)
                }
                com.example.model.CipherMode.CYBER_ROT -> {
                    // ROT-13 is symmetric
                    encryptText(input, com.example.model.CipherMode.CYBER_ROT)
                }
                com.example.model.CipherMode.GHOST_BASE64 -> {
                    val decoded = android.util.Base64.decode(input.trim(), android.util.Base64.NO_WRAP)
                    String(decoded, Charsets.UTF_8)
                }
                com.example.model.CipherMode.BINARY_PULSE -> {
                    val binaryStrings = input.trim().split(" ")
                    val bytes = binaryStrings.map { it.toInt(2).toByte() }.toByteArray()
                    String(bytes, Charsets.UTF_8)
                }
            }
        } catch (e: Exception) {
            "[CIPHER CORRUPTION: PAYLOAD UNREADABLE OR WRONG KEY]"
        }
    }
}
