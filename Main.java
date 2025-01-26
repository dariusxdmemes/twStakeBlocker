import twitter4j.*;
import twitter4j.conf.ConfigurationBuilder;

public class Main {
    public static void main(String[] args) {
        ConfigurationBuilder configurador = new ConfigurationBuilder();

        configurador.setDebugEnabled(true)
                .setOAuthConsumerKey("CONSUMER KEY")
                .setOAuthConsumerSecret("CONSUMER SECRET KEY")
                .setOAuthAccessToken("ACC TOKEN")
                .setOAuthAccessTokenSecret("TOP SECRET ACC TOKEN");

        // im not paying 200$ to Elon to use its api

        TwitterFactory tf = new TwitterFactory(configurador.build());
        Twitter twitter = tf.getInstance();

        try {
            String searchQuery = "@Stake"; // search query of what you want (in this case block this shit account)
            int page = 1; // num of pages shown
            int usersPerPage = 49; // how much usrs want
            boolean isRunning=true;

            while (isRunning) {
                ResponseList<User> users = twitter.searchUsers(searchQuery, page);
                if (users.isEmpty()) {
                    System.out.println("No users found");
                    isRunning=false;
                }
                for (User user : users) {
                    if (user.getDescription() != null && user.getDescription().contains(searchQuery)) {
                        System.out.println("Blocking: @"+user.getScreenName());
                    }
                }
                page++;
                if (page > 5) {
                    System.out.println("Limit reached!");
                    isRunning=false;
                }
            }
            System.out.println("User blocked succsessfully!");
        } catch (TwitterException e) {
            e.printStackTrace();
        }
    }
}
