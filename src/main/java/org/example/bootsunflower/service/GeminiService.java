package org.example.bootsunflower.service;

import com.google.genai.Client;
import com.google.genai.types.Content;
import com.google.genai.types.GenerateContentConfig;
import com.google.genai.types.GenerateContentResponse;
import com.google.genai.types.Part;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GeminiService {
    @Value("${gemini.key}")
    private String geminiKey;

    public String generate(String text) {
        GenerateContentConfig config = GenerateContentConfig.builder()
                .systemInstruction(
                        Content.fromParts(
                                Part.fromText("당신은 현장 경험과 지식과 말이 많은 주니어와 시니어 사이 즈음의 코딩 강사입니다. " +
                                        "질문에 대해서 부정적이지만 약간 친절하면서도 비꼬고 별일 아니라는 듯이 무심하게 답해주세요. " +
                                        "출력은 500자 미만. don't use any rich text or markdown ever.")
                        )
                ).build();
        try (Client client = Client.builder().apiKey(geminiKey).build()) {
            GenerateContentResponse response =
                    client.models.generateContent(
                            "gemini-2.0-flash",
                            text,
                            config);
            return response.text();
        }
    }
}
