package cn.graht.springbootinit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FruitVegetableNicknamesGenerator {


    /**
     * 生成四个字的形容词数组
     * @param count 形容词的数量
     * @return 包含形容词的列表
     */
    public static List<String> generateAdjectives(int count) {
        List<String> adjectives = new ArrayList<>();
        String[] adjectiveChars = {"红", "绿", "甜", "酸", "香", "脆", "鲜", "嫩", "熟", "润", "软", "硬", "圆", "扁", "长", "短", "大", "小", "多", "少"};
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            StringBuilder sb = new StringBuilder();
            for (int j = 0; j < 4; j++) { // 生成4个字符的形容词
                sb.append(adjectiveChars[random.nextInt(adjectiveChars.length)]);
            }
            adjectives.add(sb.toString());
        }

        return adjectives;
    }

    /**
     * 生成水果或蔬菜名称数组
     * @param count 水果或蔬菜名称的数量
     * @return 包含水果或蔬菜名称的列表
     */
    public static List<String> generateFruitVegetableNames(int count) {
        List<String> fruitsAndVegetables = new ArrayList<>();
        String[] fruitVegetableChars = {"苹果", "香蕉", "梨子", "桃子", "葡萄", "橙子", "柠檬", "樱桃", "草莓", "芒果", "菠萝", "西瓜", "木瓜", "猕猴桃", "柚子", "荔枝", "杏子", "李子", "橄榄", "柿子", "榴莲", "椰子", "哈密瓜", "甜橙", "蓝莓", "石榴", "白菜", "萝卜", "黄瓜", "西红柿", "茄子", "辣椒", "南瓜", "土豆", "芹菜", "大蒜"};
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String fruitOrVegetable = fruitVegetableChars[random.nextInt(fruitVegetableChars.length)];
            fruitsAndVegetables.add(fruitOrVegetable);
        }

        return fruitsAndVegetables;
    }

    /**
     * 生成随机昵称
     * @param adjectives 形容词列表
     * @param fruitsAndVegetables 水果或蔬菜名称列表
     * @param count 昵称的数量
     * @return 包含随机昵称的列表
     */
    public static List<String> generateNicknames(List<String> adjectives, List<String> fruitsAndVegetables, int count) {
        List<String> nicknames = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < count; i++) {
            String nickname = adjectives.get(random.nextInt(adjectives.size())) + fruitsAndVegetables.get(random.nextInt(fruitsAndVegetables.size()));
            nicknames.add(nickname);
        }

        return nicknames;
    }
}