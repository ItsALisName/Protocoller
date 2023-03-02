package net.alis.protocoller.bukkit.enums;

import org.jetbrains.annotations.Contract;

public enum Version {

    UNKNOWN("Unknown", (short) 0),
    v1_8("1.8.?", (short) 47),
    v1_8_3("1.8.3", (short) 48),
    v1_9_15w31a("1.9", (short) 49),
    v1_9_15w31b("1.9", (short) 50),
    v1_9_15w31c("1.9", (short) 51),
    v1_9_15w32a("1.9", (short) 52),
    v1_9_15w32b("1.9", (short) 53),
    v1_9_15w32c("1.9", (short) 54),
    v1_9_15w33a("1.9", (short) 55),
    v1_9_15w33b("1.9", (short) 56),
    v1_9_15w33c("1.9", (short) 57),
    v1_9_15w34a("1.9", (short) 58),
    v1_9_15w34b("1.9", (short) 59),
    v1_9_15w34c("1.9", (short) 60),
    v1_9_15w34d("1.9", (short) 61),
    v1_9_15w35a("1.9", (short) 62),
    v1_9_15w35b("1.9", (short) 63),
    v1_9_15w35c("1.9", (short) 64),
    v1_9_15w35d("1.9", (short) 65),
    v1_9_15w35e("1.9", (short) 66),
    v1_9_15w36a("1.9", (short) 67),
    v1_9_15w36b("1.9", (short) 68),
    v1_9_15w36c("1.9", (short) 69),
    v1_9_15w36d("1.9", (short) 70),
    v1_9_15w37a("1.9", (short) 71),
    v1_9_15w38a("1.9", (short) 72),
    v1_9_15w38b("1.9", (short) 73),
    v1_9_15w39abc("1.9", (short) 74),
    v1_9_15w40a("1.9", (short) 75),
    v1_9_15w40b("1.9", (short) 76),
    v1_9_15w41a("1.9", (short) 77),
    v1_9_15w41b("1.9", (short) 78),
    v1_9_15w42a("1.9", (short) 79),
    v1_9_15w43a("1.9", (short) 80),
    v1_9_15w43b("1.9", (short) 81),
    v1_9_15w43c("1.9", (short) 82),
    v1_9_15w44a("1.9", (short) 83),
    v1_9_15w44b("1.9", (short) 84),
    v1_9_15w45a("1.9", (short) 85),
    v1_9_15w46a("1.9", (short) 86),
    v1_9_15w47a("1.9", (short) 87),
    v1_9_15w47b("1.9", (short) 88),
    v1_9_15w47c("1.9", (short) 89),
    v1_9_15w49a("1.9", (short) 90),
    v1_9_15w49b("1.9", (short) 91),
    v1_9_15w50a("1.9", (short) 92),
    v1_9_15w51a("1.9", (short) 93),
    v1_9_15w51b("1.9", (short) 94),
    v1_9_16w02a("1.9", (short) 95),
    v1_9_16w03a("1.9", (short) 96),
    v1_9_16w04a("1.9", (short) 97),
    v1_9_16w05a("1.9", (short) 98),
    v1_9_16w05b("1.9", (short) 99),
    v1_9_16w06a("1.9", (short) 100),
    v1_9_16w07a("1.9", (short) 101),
    v1_9_16w07b("1.9", (short) 102),
    v1_9_pre1("1.9", (short) 103),
    v1_9_pre2("1.9", (short) 104),
    v1_9_pre3("1.9", (short) 105),
    v1_9_pre4("1.9", (short) 106),
    v1_9("1.9", (short) 107),
    v1_9_1("1.9.1", (short) 108),
    v1_9_2("1.9.2", (short) 109),
    v1_9_3n4("1.9.3 - 1.9.4", (short) 110),
    v1_10_16w20a("1.10", (short) 201),
    v1_10_16w21a("1.10", (short) 202),
    v1_10_16w21b("1.10", (short) 203),
    v1_10_pre1("1.10", (short) 204),
    v1_10_pre2("1.10", (short) 205),
    v1_10_n1n2("1.10 - 1.10.2", (short) 210),
    v1_11_16w32a("1.11", (short) 301),
    v1_11_16w32b("1.11", (short) 302),
    v1_11_16w33a("1.11", (short) 303),
    v1_11_16w35a("1.11", (short) 304),
    v1_11_16w36a("1.11", (short) 305),
    v1_11_16w38a("1.11", (short) 306),
    v1_11_16w39a("1.11",(short) 307),
    v1_11_16w39b("1.11", (short) 308),
    v1_11_16w39c("1.11", (short) 309),
    v1_11_16w40a("1.11", (short) 310),
    v1_11_16w41a("1.11", (short) 311),
    v1_11_16w42a("1.11", (short) 312),
    v1_11_16w43_44a("1.11", (short) 313),
    v1_11_pre1("1.11", (short) 314),
    v1_11("1.11", (short) 315),
    v1_11_1n2("1.11.1 - 1.11.2", (short) 316),
    v1_12_17w06a("1.12", (short) 317),
    v1_12_17w13a("1.12", (short) 318),
    v1_12_17w13b("1.12", (short) 319),
    v1_12_17w14a("1.12", (short) 320),
    v1_12_17w15a("1.12", (short) 321),
    v1_12_17w16a("1.12", (short) 322),
    v1_12_17w16b("1.12", (short) 323),
    v1_12_17w17a("1.12", (short) 324),
    v1_12_17w17b("1.12", (short) 325),
    v1_12_17w18a("1.12", (short) 326),
    v1_12_17w18b("1.12", (short) 327),
    v1_12_pre1("1.12", (short) 328),
    v1_12_pre2("1.12", (short) 329),
    v1_12_pre3("1.12", (short) 330),
    v1_12_pre4("1.12", (short) 331),
    v1_12_pre5("1.12", (short) 332),
    v1_12_pre6("1.12", (short) 333),
    v1_12_pre7("1.12", (short) 334),
    v1_12("1.12", (short) 335),
    v1_12_1_17w31a("1.12.1", (short) 336),
    v1_12_1_pre1("1.12.1", (short) 337),
    v1_12_1("1.12.1", (short) 338),
    v1_12_2_pre("1.12.2", (short) 339),
    v1_12_2("1.12.2", (short) 340),
    v1_13_17w43a("1.13", (short) 341),
    v1_13_17w43b("1.13", (short) 342),
    v1_13_17w45a("1.13", (short) 343),
    v1_13_17w45b("1.13", (short) 344),
    v1_13_17w46a("1.13", (short) 345),
    v1_13_17w47a("1.13", (short) 346),
    v1_13_17w47b("1.13", (short) 347),
    v1_13_17w48a("1.13", (short) 348),
    v1_13_17w49a("1.13", (short) 349),
    v1_13_17w49b("1.13", (short) 350),
    v1_13_17w50a("1.13", (short) 351),
    v1_13_18w01a("1.13", (short) 352),
    v1_13_18w02a("1.13", (short) 353),
    v1_13_18w03a("1.13", (short) 354),
    v1_13_18w03b("1.13", (short) 355),
    v1_13_18w05a("1.13", (short) 356),
    v1_13_18w06a("1.13", (short) 357),
    v1_13_18w07a("1.13", (short) 358),
    v1_13_18w07b("1.13", (short) 359),
    v1_13_18w07c("1.13", (short) 360),
    v1_13_18w08a("1.13", (short) 361),
    v1_13_18w08b("1.13", (short) 362),
    v1_13_18w09a("1.13", (short) 363),
    v1_13_18w10a("1.13", (short) 364),
    v1_13_18w10b("1.13", (short) 365),
    v1_13_18w10c("1.13", (short) 366),
    v1_13_18w10d("1.13", (short) 367),
    v1_13_18w11a("1.13", (short) 368),
    v1_13_18w14a("1.13", (short) 369),
    v1_13_18w14b("1.13", (short) 370),
    v1_13_18w15a("1.13", (short) 371),
    v1_13_18w16a("1.13", (short) 372),
    v1_13_18w19a("1.13", (short) 373),
    v1_13_18w19b("1.13", (short) 374),
    v1_13_18w20a("1.13", (short) 375),
    v1_13_18w20b("1.13", (short) 376),
    v1_13_18w20c("1.13", (short) 377),
    v1_13_18w21a("1.13", (short) 378),
    v1_13_18w21b("1.13", (short) 379),
    v1_13_18w22a("1.13", (short) 380),
    v1_13_18w22b("1.13", (short) 381),
    v1_13_18w22c("1.13", (short) 382),
    v1_13_pre1("1.13", (short) 383),
    v1_13_pre2("1.13", (short) 384),
    v1_13_pre3("1.13", (short) 385),
    v1_13_pre4("1.13", (short) 386),
    v1_13_pre5("1.13", (short) 387),
    v1_13_pre6("1.13", (short) 388),
    v1_13_pre7("1.13", (short) 389),
    v1_13_pre8("1.13", (short) 390),
    v1_13_pre9("1.13", (short) 391),
    v1_13_pre10("1.13", (short) 392),
    v1_13("1.13", (short) 393),
    v1_13_1_18w30a("1.13.1", (short) 394),
    v1_13_1_18w30b("1.13.1", (short) 395),
    v1_13_1_18w31a("1.13.1", (short) 396),
    v1_13_1_18w32a("1.13.1", (short) 397),
    v1_13_1_18w33a("1.13.1", (short) 398),
    v1_13_1_pre1("1.13.1", (short) 399),
    v1_13_1_pre2("1.13.1", (short) 400),
    v1_13_1("1.13.1", (short) 401),
    v1_13_2_pre1("1.13.2", (short) 402),
    v1_13_2_pre2("1.13.2", (short) 403),
    v1_13_2("1.13.2", (short) 404),
    v1_14_18w43ab("1.14", (short) 441),
    v1_14_18w43c("1.14", (short) 442),
    v1_14_18w44a("1.14", (short) 443),
    v1_14_18w45a("1.14", (short) 444),
    v1_14_18w46a("1.14", (short) 445),
    v1_14_18w47a("1.14", (short) 446),
    v1_14_18w47b("1.14", (short) 447),
    v1_14_18w48a("1.14", (short) 448),
    v1_14_18w48b("1.14", (short) 449),
    v1_14_18w49a("1.14", (short) 450),
    v1_14_18w50a("1.14", (short) 451),
    v1_14_19w02a("1.14", (short) 452),
    v1_14_19w03a("1.14", (short) 453),
    v1_14_19w03b("1.14", (short) 454),
    v1_14_19w03c("1.14", (short) 455),
    v1_14_19w04a("1.14", (short) 456),
    v1_14_19w04b("1.14", (short) 457),
    v1_14_19w05a("1.14", (short) 458),
    v1_14_19w06a("1.14", (short) 459),
    v1_14_19w07a("1.14", (short) 460),
    v1_14_19w08a("1.14", (short) 461),
    v1_14_19w08b("1.14", (short) 462),
    v1_14_19w09a("1.14", (short) 463),
    v1_14_19w11a("1.14", (short) 464),
    v1_14_19w11b("1.14", (short) 465),
    v1_14_19w12a("1.14", (short) 466),
    v1_14_19w12b("1.14", (short) 467),
    v1_14_19w13a("1.14", (short) 468),
    v1_14_19w13b("1.14", (short) 469),
    v1_14_19w14a("1.14", (short) 470),
    v1_14_19w14b("1.14", (short) 471),
    v1_14_pre1("1.14", (short) 472),
    v1_14_pre2("1.14", (short) 473),
    v1_14_pre3("1.14", (short) 474),
    v1_14_pre4("1.14", (short) 475),
    v1_14_pre5("1.14", (short) 476),
    v1_14("1.14", (short) 477),
    v1_14_1_pre1("1.14.1", (short) 478),
    v1_14_1_pre2("1.14.1", (short) 479),
    v1_14_1("1.14.1", (short) 480),
    v1_14_2_pre1("1.14.2", (short) 481),
    v1_14_2_pre2("1.14.2", (short) 482),
    v1_14_2_pre3("1.14.2", (short) 483),
    v1_14_2_pre4("1.14.2", (short) 484),
    v1_14_2("1.14.2", (short) 485),
    v1_14_3_pre1("1.14.3", (short) 486),
    v1_14_3_pre2("1.14.3", (short) 487),
    v1_14_3_pre3("1.14.3", (short) 488),
    v1_14_3_pre4("1.14.3", (short) 489),
    v1_14_3("1.14.3", (short) 490),
    v1_14_3_COMBAT_TEST("1.14.3(Combat Test)", (short) 500),
    v1_14_4_pre1("1.14.4", (short) 491),
    v1_14_4_pre2("1.14.4", (short) 492),
    v1_14_4_pre3("1.14.4", (short) 493),
    v1_14_4_pre4("1.14.4", (short) 494),
    v1_14_4_pre5("1.14.4", (short) 495),
    v1_14_4_pre6("1.14.4", (short) 496),
    v1_14_4_pre7("1.14.4", (short) 497),
    v1_14_4("1.14.4", (short) 498),
    v1_15_19w34a("1.15", (short) 550),
    v1_15_19w35a("1.15", (short) 551),
    v1_15_19w36a("1.15", (short) 552),
    v1_15_19w37a("1.15", (short) 553),
    v1_15_19w38a("1.15", (short) 554),
    v1_15_19w38b("1.15", (short) 555),
    v1_15_19w39a("1.15", (short) 556),
    v1_15_19w40a("1.15", (short) 557),
    v1_15_19w41a("1.15", (short) 558),
    v1_15_19w42a("1.15", (short) 559),
    v1_15_19w44a("1.15", (short) 560),
    v1_15_19w45a("1.15", (short) 561),
    v1_15_19w45b("1.15", (short) 562),
    v1_15_19w46a("1.15", (short) 563),
    v1_15_19w46b("1.15", (short) 564),
    v1_15_pre1("1.15", (short) 565),
    v1_15_pre2("1.15", (short) 566),
    v1_15_pre3("1.15", (short) 567),
    v1_15_pre4("1.15", (short) 569),
    v1_15_pre5("1.15", (short) 570),
    v1_15_pre6("1.15", (short) 571),
    v1_15_pre7("1.15", (short) 572),
    v1_15("1.15", (short) 573),
    v1_15_1_pre1("1.15.1", (short) 574),
    v1_15_1("1.15.1", (short) 575),
    v1_15_2_pre1("1.15.2", (short) 576),
    v1_15_2_pre2("1.15.2", (short) 577),
    v1_15_2("1.15.2", (short) 578),
    v1_16_20w06a("1.16", (short) 701),
    v1_16_20w07a("1.16", (short) 702),
    v1_16_20w08a("1.16", (short) 703),
    v1_16_20w09a("1.16", (short) 704),
    v1_16_20w10a("1.16", (short) 705),
    v1_16_20w11a("1.16", (short) 706),
    v1_16_20w12a("1.16", (short) 707),
    v1_16_20w13a("1.16", (short) 708),
    v1_16_20w13b("1.16", (short) 709),
    v1_16_20w14a("1.16", (short) 710),
    v1_16_20w15a("1.16", (short) 711),
    v1_16_20w16a("1.16", (short) 712),
    v1_16_20w17a("1.16", (short) 713),
    v1_16_20w18a("1.16", (short) 714),
    v1_16_20w19a("1.16", (short) 715),
    v1_16_20w20a("1.16", (short) 716),
    v1_16_20w20b("1.16", (short) 717),
    v1_16_20w21a("1.16", (short) 718),
    v1_16_20w22a("1.16", (short) 719),
    v1_16_pre1("1.16", (short) 721),
    v1_16_pre2("1.16", (short) 722),
    v1_16_pre3("1.16", (short) 725),
    v1_16_pre4("1.16", (short) 727),
    v1_16_pre5("1.16", (short) 729),
    v1_16_pre6("1.16", (short) 730),
    v1_16_pre7("1.16", (short) 732),
    v1_16_pre8("1.16", (short) 733),
    v1_16_rc1("1.16", (short) 734),
    v1_16("1.16", (short) 735),
    v1_16_1("1.16.1", (short) 736),
    v1_16_2_20w27a("1.16.2", (short) 738),
    v1_16_2_20w28a("1.16.2", (short) 740),
    v1_16_2_20w29a("1.16.2", (short) 741),
    v1_16_2_20w30a("1.16.2", (short) 743),
    v1_16_2_pre1("1.16.2", (short) 744),
    v1_16_2_pre2("1.16.2", (short) 746),
    v1_16_2_pre3("1.16.2", (short) 748),
    v1_16_2_rc1("1.16.2", (short) 749),
    v1_16_2_rc2("1.16.2", (short) 750),
    v1_16_2("1.16.2", (short) 751),
    v1_16_3_rc1("1.16.3", (short) 752),
    v1_16_3("1.16.3", (short) 753),
    v1_16_4n5("1.16.4 - 1.16.5", (short) 754),
    v1_17("1.17", (short) 755),
    v1_17_1("1.17.1", (short) 756),
    v1_18_n1("1.18 - 1.18.1", (short) 757),
    v1_18_2("1.18.2", (short) 758),
    v1_19("1.19", (short) 759),
    v1_19_1n2("1.19.1 - 1.19.2", (short) 760),
    v1_19_3("1.19.3", (short) 761),
    ANY("ANY", (short) 999);

    private final String name;
    private final short protocol;

    public final String asString() {
        return this.name;
    }

    public final short getProtocol() {
        return this.protocol;
    }

    Version(String name, short protocol) {
        this.name = name;
        this.protocol = protocol;
    }

    @Contract(pure = true)
    public static Version fromProtocol(int i) {
        for(Version version : Version.values()) {
            if(version.protocol == i) {
                return version;
            }
        }
        return UNKNOWN;
    }

    public static Version fromPackageName(String packageName) {
        packageName = packageName.replace("_", ".").replace("v", "").replace("R", "");
        for(Version version : Version.values()) {
            if(version.name.contains(packageName)) return version;
        }
        return UNKNOWN;
    }

    @Contract(pure = true)
    public static Version fromString(String name) {
        for(Version version : Version.values()) {
            if(version.name.equalsIgnoreCase(name)) {
                return version;
            }
        }
        return UNKNOWN;
    }

    public boolean greaterThanOrEqualTo(Version version) {
        return this.ordinal() >= version.ordinal();
    }

    public boolean lessThanOrEqualTo(Version version) {
        return this.ordinal() <= version.ordinal();
    }

    public boolean greaterThan(Version version) {
        return this.ordinal() > version.ordinal();
    }

    public boolean lessThan(Version version) {
        return this.ordinal() < version.ordinal();
    }

    public static boolean greaterThanOrEqualTo(Version original, Version version) {
        return original.ordinal() >= version.ordinal();
    }

    public static boolean lessThanOrEqualTo(Version original, Version version) {
        return original.ordinal() <= version.ordinal();
    }

    public static boolean greaterThan(Version original, Version version) {
        return original.ordinal() > version.ordinal();
    }

    public static boolean lessThan(Version original, Version version) {
        return original.ordinal() < version.ordinal();
    }

}

