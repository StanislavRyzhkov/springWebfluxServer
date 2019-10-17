package company.ryzhkov.market.util;

import company.ryzhkov.market.entity.key.KeyElement;
import company.ryzhkov.market.entity.text.Reply;
import company.ryzhkov.market.entity.text.Text;
import company.ryzhkov.market.entity.text.TextComponent;
import company.ryzhkov.market.entity.user.User;
import company.ryzhkov.market.repository.KeyElementRepository;
import company.ryzhkov.market.repository.TextRepository;
import company.ryzhkov.market.repository.UserRepository;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.security.Key;
import java.util.*;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.Arrays.asList;

@Slf4j
@Component
public class Bootstrap {
    private KeyElementRepository keyElementRepository;
    private TextRepository textRepository;
    private UserRepository userRepository;
    private BCryptPasswordEncoder encoder;

    @Autowired
    public void setUserRepository(UserRepository userRepository) { this.userRepository = userRepository; }

    @Autowired
    public void setTextRepository(TextRepository textRepository) { this.textRepository = textRepository; }

    @Autowired
    public void setEncoder(BCryptPasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Autowired
    public void setKeyElementRepository(KeyElementRepository keyElementRepository) {
        this.keyElementRepository = keyElementRepository;
    }

    public void createText(String kind) {
        try {
            Path a1 = Paths.get("a1.txt");
            Path a2 = Paths.get("a2.txt");

            List<String> titles = Files.readAllLines(a1);
            byte[] bytes = Files.readAllBytes(a2);

            String title = titles.get(0);
            String englishTitle = titles.get(1);

            final String[] dividedText = new String(bytes, StandardCharsets.UTF_8).split("\n\n");

            List<TextComponent> textComponents = IntStream
                    .range(0, dividedText.length)
                    .mapToObj(i -> {
                        String[] arr = dividedText[i].split("\n");
                        TextComponent textComponent = new TextComponent();
                        textComponent.setNumber(i);
                        textComponent.setTag(arr[0]);
                        textComponent.setSource(arr[1]);
                        textComponent.setContent(arr[2]);
                        return textComponent;
                    })
                    .collect(toList());

            NavigableSet<Reply> replies = new TreeSet<>();

            Text text = new Text();
            text.setTitle(title);
            text.setEnglishTitle(englishTitle);
            text.setKind(kind);
            text.setCreated(new Date());
            text.setTextComponents(textComponents);
            text.setReplies(replies);
            textRepository.insert(text).subscribe((e) -> log.info("Text {} successfully created", e.getTitle()));

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void createAdminUser() {
        List<String> roles = asList("ROLE_ADMIN", "ROLE_USER");
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter username:");
        String username = scanner.nextLine();
        System.out.println("Enter email:");
        String email = scanner.nextLine();
        System.out.println("Enter password:");
        String rawPassword = scanner.nextLine();

        String password = encoder.encode(rawPassword);

        User user = new User();
        user.setUsername(username);
        user.setPassword(password);
        user.setEmail(email);
        user.setStatus("ACTIVE");
        user.setCreated(new Date());
        user.setRoles(roles);

        userRepository.insert(user).subscribe((e) -> log.info("Admin {} successfully created", e.getUsername()));
    }

    public void createKey() {
        Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
        byte[] bytes = key.getEncoded();
        String secretString = Base64.getEncoder().encodeToString(bytes);
        KeyElement keyElement = new KeyElement();
        keyElement.setSecretString(secretString);
        keyElementRepository.insert(keyElement).subscribe(e -> log.info("Key element created"));
    }
}
