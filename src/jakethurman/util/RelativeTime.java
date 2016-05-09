package jakethurman.util;

import java.util.concurrent.TimeUnit;

public class RelativeTime {
	/* CONSTANT VALUES for timeAgoFormat messages */
	private static final long MS_IN_1_MINUTE = 1000 * 60;
	private static final long MS_IN_1_HOUR = MS_IN_1_MINUTE * 60;
	private static final long MS_IN_1_DAY = MS_IN_1_HOUR * 24;
	
	/* 
	 * Converts a time stamp in milliseconds to a string relative to the current time
	 */
	public static String timeSpanFormat(long length) {		
		// This is less than one minute so use seconds
		if (length <= MS_IN_1_MINUTE)
			return pluralize(TimeUnit.MILLISECONDS.toSeconds(length), "{0} second{s}");
		
		// This is less than one hour, so use minutes
		if (length <= MS_IN_1_HOUR)
			return pluralize(TimeUnit.MILLISECONDS.toMinutes(length), "{0} minute{s}");
		
		// This is less than one day so use hours
        if (length <= MS_IN_1_DAY)
        	return pluralize(TimeUnit.MILLISECONDS.toHours(length), "{0} hour{s}");

        // Otherwise, just use days
        return pluralize(TimeUnit.MILLISECONDS.toDays(length), "{0} day{s}");
	}
	
	/* 
	 * Converts a time stamp in milliseconds to a string relative to the current time
	 */
	public static String timeAgoFormat(long ms) {
		long now = System.currentTimeMillis();
		long diff = now - ms;
		
		// This is less than one minute so use seconds
		if (diff <= MS_IN_1_MINUTE) {
			long seconds = TimeUnit.MILLISECONDS.toSeconds(diff);
			// We don't return "0 seconds", so lie and say 1!
			return pluralize((seconds == 0 ? 1 : seconds), "{0} second{s} ago");
		}
		
		// This is less than one hour, so use minutes
		if (diff <= MS_IN_1_HOUR)
			return pluralize(TimeUnit.MILLISECONDS.toMinutes(diff), "{0} minute{s} ago");
		
		// This is less than one day so use hours
        if (diff <= MS_IN_1_DAY)
        	return pluralize(TimeUnit.MILLISECONDS.toHours(diff), "{0} hour{s} ago");

        // Otherwise, just use days
        return pluralize(TimeUnit.MILLISECONDS.toDays(diff), "{0} day{s} ago");
	}
	
	// Helper for timeAgoFormat to do string formating.
	private static String pluralize(long value, String src) {
		return src
			.replace("{0}", Long.toString(value))
			.replace("{s}", value == 1 ? "" : "s");
	}
}
