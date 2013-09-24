package com.cita.coupons.dummy;

import com.cita.coupons.R;
import com.cita.coupons.models.Coupon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Helper class for providing sample content for user interfaces created by Android template
 * wizards. <p> TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Coupon> ITEMS = new ArrayList<Coupon>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<String, Coupon> ITEM_MAP = new HashMap<String, Coupon>();

    static {
        // Add 3 sample items.
        addItem(new Coupon("1", "Zara", "WUI Discount!", "Get an additional 20% off on all sale "
                + "items", R.drawable.zara, R.drawable.zara_logo));
        addItem(new Coupon("2", "Starbucks Coffee", "WUI Happy Hour",
                "Happy hour from 6:00 pm to 7:00 pm, buy one get one Free!",
                R.drawable.sb, R.drawable.sb_logo));
        addItem(new Coupon("3", "Barnes & Noble", "Santander WUI Users Discount",
                "Save 25% on One Item with this discount!", R.drawable.bn, R.drawable.bn_logo));

    }

    private static void addItem(Coupon item) {
        ITEMS.add(item);
        ITEM_MAP.put(item.getId(), item);
    }


}
