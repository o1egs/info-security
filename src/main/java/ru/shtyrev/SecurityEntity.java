package ru.shtyrev;

import lombok.Getter;

import java.util.*;
import java.util.stream.Collectors;

public class SecurityEntity {
    private final Map<Integer, String> alphabet;
    private final String codeWord;
    @Getter
    private String word;
    @Getter
    private String encryptedWord;

    public SecurityEntity(String codeWord) {
        this.codeWord = codeWord.chars()
                .mapToObj(Character::toString)
                .distinct()
                .collect(Collectors.joining());
        this.alphabet = new HashMap<>();
        for (int i = 97, j = 0; i <= 122; i++, j++) {
            String letter = Character.toString(i);
            this.alphabet.put(j, letter);
        }
    }

    public void caesarCipherEncryptorSimple(int offset, String word) {
        this.word = word;
        StringBuilder result = new StringBuilder();
        for (char ch : word.toCharArray()) {
            if (Character.isLetter(ch)) {
                int shiftAmount = offset % 26;
                char shiftedChar;
                if (Character.isLowerCase(ch)) {
                    shiftedChar = (char) (((ch - 'a' + shiftAmount) % 26) + 'a');
                } else {
                    shiftedChar = (char) (((ch - 'A' + shiftAmount) % 26) + 'A');
                }
                result.append(shiftedChar);
            } else {
                result.append(ch);
            }
        }
        this.encryptedWord = result.toString();
    }

    public void caesarsCipherEncryptor(int offset, String word) {
        this.word = word;
        var newAlphabet = new HashMap<String, String>();
        offset = offset < alphabet.size() ? offset : offset % alphabet.size();
        int finalOffset = offset;
        alphabet.keySet().forEach(pos -> {
            if (pos + finalOffset >= alphabet.size()) {
                int newPos = (pos + finalOffset) % alphabet.size();
                newAlphabet.put(alphabet.get(pos), alphabet.get(newPos));
            } else {
                int newPos = pos + finalOffset;
                newAlphabet.put(alphabet.get(pos), alphabet.get(newPos));
            }
        });
        var temp = new HashMap<String, String>();
        newAlphabet.keySet().forEach(k -> {
            String letter = newAlphabet.get(k);
            temp.put(k.toUpperCase(), letter.toUpperCase());
        });
        newAlphabet.putAll(temp);
        this.encryptedWord = this.word.chars()
                .mapToObj(Character::toString)
                .map(s -> {
                    if (newAlphabet.containsKey(s)) {
                        return newAlphabet.get(s);
                    }
                    return s;
                })
                .collect(Collectors.joining());
    }

    public void caesarsCipherEncryptorWithCodeWord(int offset, String word) {
        this.word = word;
        var newAlphabet = new HashMap<String, String>();
        offset = offset < alphabet.size() ? offset : offset % alphabet.size();
        var codeWordList = new ArrayList<>(List.of(codeWord.split("")));
        int finalOffset = offset;
        one:
        for (int pos = 0; pos < alphabet.keySet().size(); pos++) {
            if (pos == finalOffset) {
                for (String l : codeWordList) {
                    newAlphabet.put(alphabet.get(pos), l);
                    pos++;
                }
            } else {
                if (pos + finalOffset >= alphabet.size()) {
                    int newPos = (pos + finalOffset) % alphabet.size();
                    if (codeWordList.contains(alphabet.get(newPos))) {
                        continue;
                    }
                    newAlphabet.put(alphabet.get(pos), alphabet.get(newPos));
                } else {
                    int newPos = pos + finalOffset;
                    if (codeWordList.contains(alphabet.get(newPos))) {
                        continue;
                    }
                    newAlphabet.put(alphabet.get(pos), alphabet.get(newPos));
                }
            }
        }
        var temp = new HashMap<String, String>();
        newAlphabet.keySet().forEach(k -> {
            String letter = newAlphabet.get(k);
            temp.put(k.toUpperCase(), letter.toUpperCase());
        });
        newAlphabet.putAll(temp);
        this.encryptedWord = this.word.chars()
                .mapToObj(Character::toString)
                .map(s -> {
                    if (newAlphabet.containsKey(s)) {
                        return newAlphabet.get(s);
                    }
                    return s;
                })
                .collect(Collectors.joining());
    }
}
