package com.jfund.testutils.datagenerating;


public class PrettyWordGenerator {
    private String currentWord;

    private final byte endCharByte;

    private final int lengthWord;
    private int tempWordCharIndex;

    public PrettyWordGenerator(int lengthWord) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < lengthWord; i++) {
            stringBuilder.append('a');
        }
        currentWord = stringBuilder.toString();

        byte startCharByte = (byte) 'a';
        endCharByte = (byte)'z';

        this.lengthWord = lengthWord;
        this.tempWordCharIndex = 0;
    }

    public String next() throws IndexOutOfBoundsException{
        String nextWord = getNextWord();
        this.currentWord = nextWord;
        this.tempWordCharIndex = this.tempWordCharIndex == this.lengthWord - 1 ? 0: this.tempWordCharIndex + 1;

        return this.currentWord;
    }

    public boolean hasNext(){
        try{
            getNextWord();
            return true;
        }catch (IndexOutOfBoundsException e){
            return false;
        }
    }

    private String getNextWord() throws IndexOutOfBoundsException{
        char[] charArray = this.currentWord.toCharArray();
        byte nextCharByte = (byte) ((byte)charArray[this.tempWordCharIndex] + 1);
        if(nextCharByte > this.endCharByte)
            throw new IndexOutOfBoundsException("pretty words were overed");
        charArray[this.tempWordCharIndex] = (char)nextCharByte;
        return new String(charArray);
    }

    public String getCurrentWord() {
        return currentWord;
    }
}
