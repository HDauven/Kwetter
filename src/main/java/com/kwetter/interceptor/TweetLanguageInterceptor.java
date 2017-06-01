package com.kwetter.interceptor;

import com.kwetter.model.Tweet;

import javax.interceptor.AroundInvoke;
import javax.interceptor.InvocationContext;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by hein on 5/24/17.
 */
public class TweetLanguageInterceptor {

    // Foul words to match against
    Pattern pattern = Pattern.compile("shit|crap|damn|dummy", Pattern.CASE_INSENSITIVE);

    @AroundInvoke
    public Object filterFoulLanguage(InvocationContext context) throws Exception {
        Object[] parameters = context.getParameters();
        if (parameters.length > 0 && parameters[0] instanceof Tweet) {
            // Retrieve the tweet from the context
            Tweet tweet = (Tweet) parameters[0];
            System.out.println("Before filter: " + tweet.getDescription());
            // Get the text to match against
            Matcher matcher = pattern.matcher(tweet.getDescription());
            StringBuffer buffer = new StringBuffer();
            while (matcher.find()) {
                // Build a character list based on the size of the found foul word and replace it with '*'
                String replacement = new String(new char[matcher.group().length()]).replace("\0", "*");
                matcher.appendReplacement(buffer, replacement);
            }
            matcher.appendTail(buffer);
            tweet.setDescription(buffer.toString());
            System.out.println("After filter: " + tweet.getDescription());
            parameters[0] = tweet;
            context.setParameters(parameters);
        }
        return context.proceed();
    }
}
