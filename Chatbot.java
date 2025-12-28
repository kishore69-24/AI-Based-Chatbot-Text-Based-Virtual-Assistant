import java.io.*;
import java.util.*;

public class Chatbot {
    private static final String RESPONSE_FILE = "responses.txt";
    private static Map<String, String> responseMap = new HashMap<>();

    public static void main(String[] args) {
        loadResponses(); // Load responses from file
        Scanner scanner = new Scanner(System.in);

        System.out.println("🤖 Chatbot: Hello! Ask me anything or type 'exit' to quit.");
        
        while (true) {
            System.out.print("You: ");
            String userInput = scanner.nextLine().trim().toLowerCase();

            if (userInput.equals("exit")) {
                System.out.println("🤖 Chatbot: Goodbye! Have a great day.");
                break;
            }

            if (responseMap.containsKey(userInput)) {
                System.out.println("🤖 Chatbot: " + responseMap.get(userInput));
            } else {
                System.out.println("🤖 Chatbot: I don't know the answer. Can you teach me?");
                System.out.print("Enter the correct response: ");
                String newResponse = scanner.nextLine();
                responseMap.put(userInput, newResponse);
                saveResponse(userInput, newResponse);
                System.out.println("🤖 Chatbot: Got it! I'll remember this.");
            }
        }

        scanner.close();
    }

    // Load responses from file
    private static void loadResponses() {
        File file = new File(RESPONSE_FILE);
        if (!file.exists()) return;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("=", 2);
                if (parts.length == 2) {
                    responseMap.put(parts[0].trim().toLowerCase(), parts[1].trim());
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading responses.");
        }
    }

    // Save new response to file
    private static void saveResponse(String question, String answer) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(RESPONSE_FILE, true))) {
            bw.write(question + "=" + answer);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error saving response.");
        }
    }
}

