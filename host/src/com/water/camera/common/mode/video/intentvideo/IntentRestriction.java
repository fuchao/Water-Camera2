/*
 *   Copyright Statement:
 *
 *     This software/firmware and related documentation ("MediaTek Software") are
 *     protected under relevant copyright laws. The information contained herein is
 *     confidential and proprietary to MediaTek Inc. and/or its licensors. Without
 *     the prior written permission of MediaTek inc. and/or its licensors, any
 *     reproduction, modification, use or disclosure of MediaTek Software, and
 *     information contained herein, in whole or in part, shall be strictly
 *     prohibited.
 *
 *     MediaTek Inc. (C) 2016. All rights reserved.
 *
 *     BY OPENING THIS FILE, RECEIVER HEREBY UNEQUIVOCALLY ACKNOWLEDGES AND AGREES
 *    THAT THE SOFTWARE/FIRMWARE AND ITS DOCUMENTATIONS ("MEDIATEK SOFTWARE")
 *     RECEIVED FROM MEDIATEK AND/OR ITS REPRESENTATIVES ARE PROVIDED TO RECEIVER
 *     ON AN "AS-IS" BASIS ONLY. MEDIATEK EXPRESSLY DISCLAIMS ANY AND ALL
 *     WARRANTIES, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE IMPLIED
 *     WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE OR
 *     NONINFRINGEMENT. NEITHER DOES MEDIATEK PROVIDE ANY WARRANTY WHATSOEVER WITH
 *     RESPECT TO THE SOFTWARE OF ANY THIRD PARTY WHICH MAY BE USED BY,
 *     INCORPORATED IN, OR SUPPLIED WITH THE MEDIATEK SOFTWARE, AND RECEIVER AGREES
 *     TO LOOK ONLY TO SUCH THIRD PARTY FOR ANY WARRANTY CLAIM RELATING THERETO.
 *     RECEIVER EXPRESSLY ACKNOWLEDGES THAT IT IS RECEIVER'S SOLE RESPONSIBILITY TO
 *     OBTAIN FROM ANY THIRD PARTY ALL PROPER LICENSES CONTAINED IN MEDIATEK
 *     SOFTWARE. MEDIATEK SHALL ALSO NOT BE RESPONSIBLE FOR ANY MEDIATEK SOFTWARE
 *     RELEASES MADE TO RECEIVER'S SPECIFICATION OR TO CONFORM TO A PARTICULAR
 *     STANDARD OR OPEN FORUM. RECEIVER'S SOLE AND EXCLUSIVE REMEDY AND MEDIATEK'S
 *     ENTIRE AND CUMULATIVE LIABILITY WITH RESPECT TO THE MEDIATEK SOFTWARE
 *     RELEASED HEREUNDER WILL BE, AT MEDIATEK'S OPTION, TO REVISE OR REPLACE THE
 *     MEDIATEK SOFTWARE AT ISSUE, OR REFUND ANY SOFTWARE LICENSE FEES OR SERVICE
 *     CHARGE PAID BY RECEIVER TO MEDIATEK FOR SUCH MEDIATEK SOFTWARE AT ISSUE.
 *
 *     The following software/firmware and/or related documentation ("MediaTek
 *     Software") have been modified by MediaTek Inc. All revisions are subject to
 *     any receiver's applicable license agreements with MediaTek Inc.
 */
package com.water.camera.common.mode.video.intentvideo;

import com.water.camera.common.relation.Relation;
import com.water.camera.common.relation.RelationGroup;
import com.mediatek.camera.portability.SystemProperties;


/**
 * Intent video mode restriction.
 */

public class IntentRestriction {

    private static final String INTENT_VIDEO_KEY =
            "com.water.camera.common.mode.video.intentvideo.IntentVideoMode";
    private static RelationGroup sRelationGroup = new RelationGroup();
    private static RelationGroup sRecordingRelationGroupForMode = new RelationGroup();

    static {
        sRelationGroup.setHeaderKey(INTENT_VIDEO_KEY);
        if (SystemProperties.getInt("vendor.mtk.camera.app.fd.video", 0) == 0) {
            sRelationGroup.setBodyKeys("key_video_quality,key_focus,key_face_detection");
            sRelationGroup.addRelation(
                    new Relation.Builder(INTENT_VIDEO_KEY, "preview")
                            .addBody("key_video_quality", "0", "0")
                            .addBody("key_focus", "continuous-video", "continuous-video,auto")
                            .addBody("key_face_detection", "off", "off")
                            .build());
        } else {
            sRelationGroup.setBodyKeys("key_video_quality,key_focus");
            sRelationGroup.addRelation(
                    new Relation.Builder(INTENT_VIDEO_KEY, "preview")
                            .addBody("key_video_quality", "0", "0")
                            .addBody("key_focus", "continuous-video", "continuous-video,auto")
                            .build());
        }
    }

    static {
        sRecordingRelationGroupForMode.setHeaderKey(INTENT_VIDEO_KEY);
        sRecordingRelationGroupForMode.setBodyKeys("key_focus");
        if (SystemProperties.getInt("vendor.mtk.camera.app.fd.video", 0) == 0) {
            sRecordingRelationGroupForMode.addRelation(
                    new Relation.Builder(INTENT_VIDEO_KEY, "recording")
                            .addBody("key_focus", "auto", "auto")
                            .build());
        }
        sRecordingRelationGroupForMode.addRelation(
                new Relation.Builder(INTENT_VIDEO_KEY, "stop-recording")
                        .addBody("key_focus", "continuous-video", "continuous-video,auto")
                        .build());
    }

    /**
     * Intent video restriction witch are have setting ui.
     *
     * @return restriction list.
     */
    static RelationGroup getPreviewRelation() {
        return sRelationGroup;
    }

    /**
     * Intent video restriction for focus mode witch are during recording.
     *
     * @return restriction list.
     */
    static RelationGroup getRecordingRelationGroupForMode() {
        return sRecordingRelationGroupForMode;
    }

}
