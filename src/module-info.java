module voogasalad_Crouton {
    requires java.xml;
    requires java.desktop;
    requires javafx.base;
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.media;
    requires javafx.web;

    requires xstream;
	requires org.junit.jupiter.api;
   // requires junit;
//	requires org.junit.jupiter.params;

    opens voogasalad.gameEngine to xstream;

    exports voogasalad.view;
    exports voogasalad.gameEngine;
}
