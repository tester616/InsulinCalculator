package com.example.x.bolusopas;


import android.util.Log;

public class Language
{
    //Language.
    public int LANGUAGE;

    //Buttons.
    public String ADD_BUTTON = "",

    //CheckBoxes.
    ALLOW_ALERT_CHANGES_IN_SETTINGS_CHECK_BOX = "",

    //EditTexts
    ANSWER_EDIT_TEXT = "",

    //TextViews.
    ACCESS_TEXT_VIEW = "",

    //Spinners.
    REPEAT_SPINNER_0 = "",

    //Messages.
    ACCOUNT_CREATION_LOCKED = "",

    //Dialogs.
    DIALOG_CALCULATION_FAILED = "",
    DIALOG_RESULT_SAVED = "",
    DIALOG_SETTINGS_SAVED = "",

    //Radio buttons
    RADIO_TEMPLATE = "";

    public Language()
    {

    }

    public Language(int language)
    {
        LANGUAGE = language;

        if(LANGUAGE == GV.LANGUAGE_ENGLISH) {
            DIALOG_CALCULATION_FAILED = "Both fields need to have values.";
            DIALOG_RESULT_SAVED = "Result saved.";
            DIALOG_SETTINGS_SAVED = "Settings saved.";
        }
        else if(LANGUAGE == GV.LANGUAGE_FINNISH) {
            DIALOG_CALCULATION_FAILED = "Molemmille kentille on annettava arvot.";
            DIALOG_RESULT_SAVED = "Tulos tallennettu.";
            DIALOG_SETTINGS_SAVED = "Asetukset tallennettu.";
        }
        else if(LANGUAGE == GV.LANGUAGE_SWEDISH) {
            DIALOG_CALCULATION_FAILED = "Båda fälten måste ha värden.";
            DIALOG_RESULT_SAVED = "Resultatet är sparat.";
            DIALOG_SETTINGS_SAVED = "Inställningar sparade.";
        }
        else {
            Log.v("Language error", "No such language supported.");
        }
    }
}
