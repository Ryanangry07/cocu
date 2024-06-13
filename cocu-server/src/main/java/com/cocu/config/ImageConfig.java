package com.cocu.config;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

@Configuration
public class ImageConfig {

    @Bean
    public DefaultKaptcha getDefaultKaptcha(){
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        Properties properties = new Properties();
        // has border
        properties.setProperty(Constants.KAPTCHA_BORDER, "yes");
        // border color
        properties.setProperty(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
        // font color
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "blue");
        // image width
        properties.setProperty(Constants.KAPTCHA_IMAGE_WIDTH, "200");
        // image height
        properties.setProperty(Constants.KAPTCHA_IMAGE_HEIGHT, "36");
        // the character generated
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_STRING, "0123456789");
        // remove noise line
        properties.setProperty(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        // font size
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "34");
        // font style
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "Arial,Courier,Georgia");
        // the number of verification code
        properties.setProperty(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        // image // WaterRipple, ShadowGimpy and FishEyeGimpy
        properties.setProperty(Constants.KAPTCHA_OBSCURIFICATOR_IMPL, "com.google.code.kaptcha.impl.ShadowGimpy");
        Config config = new Config(properties);
        defaultKaptcha.setConfig(config);
        return defaultKaptcha;
    }
}
