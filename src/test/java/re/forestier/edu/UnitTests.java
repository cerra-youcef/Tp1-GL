package re.forestier.edu;

import org.junit.jupiter.api.*;
import re.forestier.edu.rpg.UpdatePlayer;
import re.forestier.edu.rpg.player;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

public class UnitTests {

    @Test
    @DisplayName("mutation test")
    void testAddXp() {
        player p = new player("CERRA", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        assertFalse(UpdatePlayer.addXp(p,1));
    }
    @Test
    @DisplayName("mutation test")
    void testPoints() {
        player p = new player("CERRA", "Grognak le barbare", "DWARF", 100, new ArrayList<>());

        p.healthpoints = 10;
        p.currenthealthpoints = 3;
        p.inventory.add("Holy Elixir");

        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, equalTo(5));

        p=new player("CERRA", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 3;
        p.inventory.add("Magic Bow");
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, equalTo(3));

        p=new player("CERRA", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.healthpoints = 10;
        p.currenthealthpoints = 3;
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, equalTo(4));

        UpdatePlayer.addXp(p,27);
        UpdatePlayer.majFinDeTour(p);
        assertThat(p.currenthealthpoints, equalTo(6));







    }


    @Test
    @DisplayName("UpdatePlayer(player player) test")
    void testUpdatePlayer() {
        player player = new player("CERRA", "Grognak le barbare", "DWARF", 100, new ArrayList<>());
        player.currenthealthpoints=0;
        assertEquals(player.currenthealthpoints, 0);
        UpdatePlayer.majFinDeTour(player);

        player.healthpoints=0;
        player.currenthealthpoints=player.healthpoints/2-1;
        player.inventory.add("Holy Elixir");
        assertEquals(player.inventory.get(0), "Holy Elixir");
        assertThat(player.currenthealthpoints< player.healthpoints/2, is(true));
        assertThat(player.getAvatarClass(), is("DWARF"));
        UpdatePlayer.majFinDeTour(player);


        player.currenthealthpoints=player.healthpoints/2-1;
        assertThat(player.currenthealthpoints< player.healthpoints/2, is(true));
        player.inventory.remove("Holy Elixir");
        player.inventory.add("!Holy Elixir");
        assertThat(player.inventory.contains("Holy Elixir"), is(false));
        UpdatePlayer.majFinDeTour(player);

        player.currenthealthpoints=1;
        assertThat(player.currenthealthpoints> player.healthpoints/2, is(true));
        UpdatePlayer.majFinDeTour(player);

        player = new player("CERRA", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        player.currenthealthpoints=0;

        player.healthpoints=0;
        player.currenthealthpoints=player.healthpoints/2-1;
        assertThat(player.currenthealthpoints< player.healthpoints/2, is(true));
        assertThat(player.getAvatarClass()=="ADVENTURER", is(true));
        UpdatePlayer.addXp(player,27);

        assertEquals(player.retrieveLevel(),3 );// Level 2, doesn't change because we didn't passed 27 points
        System.out.println(player.retrieveLevel());
        UpdatePlayer.majFinDeTour(player);

        player.currenthealthpoints=player.healthpoints/2-1;
        assertThat(player.currenthealthpoints< player.healthpoints/2, is(true));
        UpdatePlayer.addXp(player,-27);// return to the first level (level 1)

        System.out.println(player.retrieveLevel());
        UpdatePlayer.majFinDeTour(player);

        player = new player("CERRA", "Grognak le barbare", "ARCHER", 100, new ArrayList<>());
        assertEquals(player.retrieveLevel(),1); // 0 points ==> Level 1
        UpdatePlayer.addXp(player,20);

        assertEquals(player.retrieveLevel(),2 ); // Level up! from Level 1 to Level 2
        UpdatePlayer.addXp(player,1);

        assertEquals(player.retrieveLevel(),2 );// Level 2, doesn't change because we didn't passed 27 points (21 points)




        player.currenthealthpoints=0;
        player.healthpoints=0;
        player.currenthealthpoints=player.healthpoints/2-1;
        player.inventory.add("Magic Bow");
        assertThat(player.currenthealthpoints< player.healthpoints/2, is(true));
        assertThat(player.getAvatarClass()=="DWARF" && player.getAvatarClass()=="ADVENTURER", is(false));
        assertEquals(player.inventory.contains("Magic Bow"), true);
        UpdatePlayer.majFinDeTour(player);

        player.inventory.remove("Magic Bow");
        assertEquals(player.inventory.contains("Magic Bow"), false);
        UpdatePlayer.majFinDeTour(player);

    }


    @Test
    @DisplayName("Impossible to have negative money")
    void testNegativeMoney() {
        player p = new player("Florian", "Grognak le barbare", "!ADVENTURER!ARCHER!DWARF", 100, new ArrayList<>());


        p = new player("Florian", "Grognak le barbare", "ADVENTURER", 100, new ArrayList<>());
        p.addMoney(100);
        int amount = 0;
        p.removeMoney(amount);
        assertThat(p.money-amount<0, is(false));
        UpdatePlayer.addXp(p,58);

        assertThat(p.getXp()<58 && p.getXp()>=27,is(false));
        UpdatePlayer.addXp(p,54);

        assertThat(p.getXp()<112 && p.getXp()>=57,is(false));




        try {
            p.removeMoney(300);
            amount=300;
            assertThat(p.money-amount<0, is(true));
        } catch (IllegalArgumentException e) {
            return;
        }

        fail();

    }

}
