package com.example.gofishgui.fish;

import com.example.gofishgui.activities.MainActivity;

import java.util.ArrayList;
import java.util.Random;

public class FishDumbAI {

    private ArrayList<FishCard> humanHand;
    private ArrayList<FishCard> computerHand;
    private ArrayList<FishCard> deck;
    int value;
    FishActionObject fishActionObject;
    private fishGameState fish = fishGameState.getInstance();

    public FishDumbAI(ArrayList<FishCard> humanHand, ArrayList<FishCard> computerHand, ArrayList<FishCard> deck) {
        this.humanHand = fish.humanHand;
        this.computerHand = fish.computerHand;
        this.deck = fish.deck;
        this.fishActionObject = new FishActionObject(fish.computerHand, fish.humanHand, fish.deck);
        //this.fish = new fishGameState();

    }
    public interface OnAskListener{
        void onAsk(int value);
    }
    private OnAskListener listener;
    public void setOnAskListener(OnAskListener listener) {
        this.listener = listener;
    }

    //Random number generator for a value from 1-13
    public int randomVal() {
        Random rand = new Random();
        int toRtn = rand.nextInt(13) + 1;
        return toRtn;
    }

    public boolean dumbAsk() {

        value = randomVal();
        fishActionObject.askForCard(value, 1);
        fishActionObject.checkForFour(fish.humanHand, fish.computerHand, value);
        //fish.isGameOver(); //call isGameOver

        // Notify the listener that the AI has asked for a card.
        if (listener != null) {
            listener.onAsk(value);
        }

        System.out.print("User hand after ask: ");
        for (FishCard card : humanHand) {
            System.out.print(card.getValue() + " ");
        }
        System.out.println(" ");
        System.out.print("Computer hand after ask: ");
        for (FishCard card : computerHand) {
            System.out.print(card.getValue() + " ");
        }
        System.out.println(" ");
        System.out.print("Deck after ask: ");
        for (FishCard card : deck) {
            System.out.print(card.getValue() + " ");
        }
        System.out.println(" ");

        fish.setHumanHand(humanHand); //updates the humanHand in fishGameState
        fish.setComputerHand(computerHand);; //updates the computerHand in fishGameState
        fish.setDeck(deck); //updates the deck in fishGameState

        return true;
    }

}
