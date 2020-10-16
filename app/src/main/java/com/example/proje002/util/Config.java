package com.example.proje002.util;

public class Config {
    public static final String DATABASE_NAME = "araclar";

    //ARAC TABLOSU SÜTUNLARI
    public static final String TABLE_ARAC = "arac";
    public static final String COLUMN_ARAC_ID = "_id";
    public static final String COLUMN_PILAKA = "pilaka";
    public static final String COLUMN_MARKA = "marka";


    //BAKIM TABLOSU SÜTUNLARI
    public static final String TABLE_BAKIM = "bakim";
    public static final String COLUMN_BAKIM_ID = "_id";
    public static final String COLUMN_BAKIM1 = "bakim1";
    public static final String COLUMN_BAKIM2 = "bakim2";
    public static final String COLUMN_REGISTRATION_NUMBER = "fk_registration_no";

    public static final String TITLE = "title";
    public static final String CREATE_ARAC = "create_arac";
    public static final String UPDATE_ARAC = "update_arac";
    public static final String CREATE_BAKIM = "create_bakim";
    public static final String UPDATE_BAKIM = "update_bakim";
    public static final String ARAC_REGISTRATION = "arac_registration";

}

