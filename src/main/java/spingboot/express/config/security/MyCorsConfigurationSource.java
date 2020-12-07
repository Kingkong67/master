package spingboot.express.config.security;

import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Component
public class MyCorsConfigurationSource implements CorsConfigurationSource {

    private static final List<String> ALLOWED_HEADER = new ArrayList<>();
    private static final List<String> ALLOWED_METHOD = new ArrayList<>();
    private static final List<String> ALLOWED_ORIGIN = new ArrayList<>();
    static {
        ALLOWED_HEADER.add("x-requested-with");
        ALLOWED_HEADER.add("Content-Type");
        ALLOWED_HEADER.add("Authorization");
        ALLOWED_HEADER.add("Content-Type");
        ALLOWED_HEADER.add("credential");
        ALLOWED_HEADER.add("X-XSRF-TOKEN");
        ALLOWED_HEADER.add("timestamp");
        ALLOWED_HEADER.add("userCode");
        ALLOWED_HEADER.add("platformKey");
        ALLOWED_HEADER.add("tokenId");
    }
    static {
        ALLOWED_METHOD.add("GET");
        ALLOWED_METHOD.add("POST");
        ALLOWED_METHOD.add("PUT");
        ALLOWED_METHOD.add("DELETE");

    }
    static {
        ALLOWED_ORIGIN.add("*");
    }
    private static final String MAX_AGE = "3600";

    @Override
    public CorsConfiguration getCorsConfiguration(HttpServletRequest httpServletRequest) {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.setAllowedHeaders(ALLOWED_HEADER);
        corsConfiguration.setAllowedMethods(ALLOWED_HEADER);
        corsConfiguration.setAllowedOrigins(ALLOWED_ORIGIN);
        corsConfiguration.setMaxAge(Long.parseLong(MAX_AGE));
        return corsConfiguration;
    }
}
