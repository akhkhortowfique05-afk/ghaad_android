package com.ghaad.shield;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;

public class GhaadGuardService extends AccessibilityService {
    private static final String[] BLOCKED_KEYWORDS = {
        "porn", "xvideos", "xnxx", "pornhub", "xhamster", "nsfw", "xxx", "nude", "sex video", "brazzers", "onlyfans"
    };

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
        AccessibilityNodeInfo root = getRootInActiveWindow();
        if (root == null) return;

        String pkg = event.getPackageName() != null ? event.getPackageName().toString().toLowerCase() : "";
        String screenText = collectText(root).toLowerCase();

        // 1. ANTI-BYPASS: If user opens Settings and tries to view/uninstall/stop Project Ghaad -> Kick to Home Screen!
        if (pkg.contains("settings") || pkg.contains("packageinstaller")) {
            if (screenText.contains("project ghaad") || screenText.contains("ghaad shield")) {
                performGlobalAction(GLOBAL_ACTION_HOME);
                return;
            }
        }

        // 2. CONTENT BLOCKER: If explicit content/sites detected on screen -> Instantly close & go Home!
        for (String word : BLOCKED_KEYWORDS) {
            if (screenText.contains(word)) {
                performGlobalAction(GLOBAL_ACTION_BACK);
                performGlobalAction(GLOBAL_ACTION_HOME);
                return;
            }
        }
    }

    private String collectText(AccessibilityNodeInfo node) {
        if (node == null) return "";
        StringBuilder sb = new StringBuilder();
        if (node.getText() != null) sb.append(node.getText()).append(" ");
        if (node.getContentDescription() != null) sb.append(node.getContentDescription()).append(" ");
        for (int i = 0; i < node.getChildCount(); i++) {
            sb.append(collectText(node.getChild(i)));
        }
        return sb.toString();
    }

    @Override
    public void onInterrupt() {}
}
