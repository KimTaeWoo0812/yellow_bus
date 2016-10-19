package net.computeering.newschoolbus.SchoolListPackage;

/**
 * Created by Seongho on 2016-03-24.
 */
import java.text.Collator;
import java.util.Comparator;

import android.content.Context;
import android.graphics.drawable.Drawable;

public class ListData {
    /**
     * 리스트 정보를 담고 있을 객체 생성
     */
    // 아이콘
    public Drawable mIcon;
    // 제목
    public String schoolName;

    // 날짜
    public String role;

    /**
     * 알파벳 이름으로 정렬
     */
    public static final Comparator<ListData> ALPHA_COMPARATOR = new Comparator<ListData>() {
        private final Collator sCollator = Collator.getInstance();

        @Override
        public int compare(ListData mListDate_1, ListData mListDate_2) {
            return sCollator.compare(mListDate_1.schoolName, mListDate_2.schoolName);
        }
    };
}