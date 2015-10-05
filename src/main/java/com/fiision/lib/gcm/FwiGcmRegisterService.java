//  Project name: FwiGcm
//  File name   : FwiGcmRegisterService.java
//
//  Author      : Phuc, Tran Huu
//  Created date: 8/17/15
//  Version     : 1.00
//  --------------------------------------------------------------
//  Copyright (C) 2012, 2015 Fiision Studio.
//  All Rights Reserved.
//  --------------------------------------------------------------
//
//  Permission is hereby granted, free of charge, to any person obtaining  a  copy
//  of this software and associated documentation files (the "Software"), to  deal
//  in the Software without restriction, including without limitation  the  rights
//  to use, copy, modify, merge,  publish,  distribute,  sublicense,  and/or  sell
//  copies of the Software,  and  to  permit  persons  to  whom  the  Software  is
//  furnished to do so, subject to the following conditions:
//
//  The above copyright notice and this permission notice shall be included in all
//  copies or substantial portions of the Software.
//
//  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF  ANY  KIND,  EXPRESS  OR
//  IMPLIED, INCLUDING BUT NOT  LIMITED  TO  THE  WARRANTIES  OF  MERCHANTABILITY,
//  FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO  EVENT  SHALL  THE
//  AUTHORS OR COPYRIGHT HOLDERS  BE  LIABLE  FOR  ANY  CLAIM,  DAMAGES  OR  OTHER
//  LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING  FROM,
//  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN  THE
//  SOFTWARE.
//
//
//  Disclaimer
//  __________
//  Although reasonable care has been taken to  ensure  the  correctness  of  this
//  software, this software should never be used in any application without proper
//  testing. Fiision Studio disclaim  all  liability  and  responsibility  to  any
//  person or entity with respect to any loss or damage caused, or alleged  to  be
//  caused, directly or indirectly, by the use of this software.

package com.fiision.lib.gcm;


import android.app.*;
import android.content.*;
import android.provider.*;

import com.google.android.gms.gcm.*;
import com.google.android.gms.iid.*;


public final class FwiGcmRegisterService extends IntentService {
    static public final String DEVICE_ID       = "DEVICE_ID";
    static public final String REGISTRATION_ID = "REGISTRATION_ID";


    // Class's constructors
    public FwiGcmRegisterService() {
        super(FwiGcmRegisterService.class.getSimpleName());
    }


    /** Register remote notification. */
    @Override
    protected void onHandleIntent(Intent intent) {
        InstanceID instanceID = InstanceID.getInstance(this);
        boolean isSuccessed = false;

        do {
            try {
                // Register with GCM
                String registrationId = instanceID.getToken(FwiGcm.getProjectId(), GoogleCloudMessaging.INSTANCE_ID_SCOPE);
                String deviceId = Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);

                // Broadcast message
                Intent broadcastIntent = new Intent(FwiGcmRegisterService.class.getName());
                intent.putExtra(REGISTRATION_ID, registrationId);
                intent.putExtra(DEVICE_ID, deviceId);

                sendBroadcast(broadcastIntent);
                isSuccessed = true;
            }
            catch (Exception ex) {
                // Ignore the exception, re-try
            }
        } while (!isSuccessed);
    }
}
