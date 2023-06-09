package com.example.gofishgui.fish;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.gofishgui.activities.MainActivity;

import java.util.ArrayList;

public class FishActionObject {
    // instance variables
    private ArrayList<FishCard> currHand;
    private ArrayList<FishCard> otherHand;
    private ArrayList<FishCard> deck;
    private fishGameState fish = fishGameState.getInstance();


    // constructor
    public FishActionObject(ArrayList<FishCard> currHand, ArrayList<FishCard> otherHand, ArrayList<FishCard> deck) {
        this.currHand = currHand;
        this.otherHand = otherHand;
        this.deck = deck;
    }

    // askforcard method
    public boolean askForCard(int value, int playerIdx) {
        ArrayList<FishCard> cardsToRemove = new ArrayList<>();
        FishActionObject fishActionObject = new FishActionObject(fish.humanHand, fish.computerHand, fish.deck);
        boolean hasCard = false;
        for (FishCard card : otherHand) {
            if (card.getValue() == value) {
                currHand.add(card);
                cardsToRemove.add(card);
                hasCard = true;
            }
        }
        if (playerIdx == 0) {
            if (!(fish.priority.contains(value))) {
                fish.priority.add(value);
            }
            if (fish.doNotAsk.contains(value)) {
                Integer integerToRemove = value;
                fish.doNotAsk.remove(integerToRemove);
            }
        } else {
            fish.setComputerAsk(value);
            fish.doNotAsk.add(value);
        }

        if (hasCard == true) {
            otherHand.removeAll(cardsToRemove);
            fishActionObject.checkForFour(fish.humanHand, fish.computerHand, value);
            return true;
        }
        else {
            System.out.println("Go Fish!");
            fishActionObject.checkForFour(fish.humanHand, fish.computerHand, drawCard(playerIdx));
            fish.nextPlayer();
            return false;
        }
    }
    public int drawCard(int playerIdx) {
        FishCard card = null;
        if (!deck.isEmpty()) {
            card = deck.remove(0);
            if (playerIdx == 0) {
                fish.humanHand.add(card);
                checkForFour(fish.humanHand, fish.computerHand, card.getValue());
            } else {
                fish.computerHand.add(card);
                checkForFour(fish.computerHand, fish.humanHand, card.getValue());
            }
        }
        return card.getValue();
    }

    public void checkForFour(ArrayList<FishCard> currHand, ArrayList<FishCard> otherHand, int value) {
        int playerCount = 0;
        int opponentCount = 0;
        ArrayList<FishCard> cardsToRemove = new ArrayList<>();
        for (FishCard card : currHand) {
            if (card.getValue() == value) {
                playerCount++;
            }
        }
        for (FishCard card : otherHand) {
            if (card.getValue() == value) {
                opponentCount++;
            }
        }
        if (playerCount == 4) {
            fish.playerScore += 4;
            for (FishCard card : currHand) {
                if (card.getValue() == value) {
                    cardsToRemove.add(card);
                }
            }
        }
        if (opponentCount == 4) {
            fish.opponentScore += 4;
            for (FishCard card : otherHand) {
                if (card.getValue() == value) {
                    cardsToRemove.add(card);
                }
            }
        }
        if (!cardsToRemove.isEmpty()) {
            currHand.removeAll(cardsToRemove);
            otherHand.removeAll(cardsToRemove);
        }
    }
}

