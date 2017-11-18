package com.android.internal.telephony;

import android.content.Intent;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.telecom.PhoneAccount;
import android.telephony.CellInfo;
import android.telephony.IccOpenLogicalChannelResponse;
import android.telephony.ModemActivityInfo;
import android.telephony.NeighboringCellInfo;
import android.telephony.RadioAccessFamily;

import java.util.List;

public interface ITelephony extends IInterface {

    public static abstract class Stub extends Binder implements ITelephony {
        private static final String DESCRIPTOR = "com.android.internal.telephony.ITelephony";
        static final int TRANSACTION_answerRingingCall = 5;
        static final int TRANSACTION_answerRingingCallForSubscriber = 6;
        static final int TRANSACTION_call = 2;
        static final int TRANSACTION_canChangeDtmfToneLength = 113;
        static final int TRANSACTION_checkCarrierPrivilegesForPackage = 97;
        static final int TRANSACTION_checkCarrierPrivilegesForPackageAnyPhone = 98;
        static final int TRANSACTION_dial = 1;
        static final int TRANSACTION_disableDataConnectivity = 39;
        static final int TRANSACTION_disableLocationUpdates = 36;
        static final int TRANSACTION_disableLocationUpdatesForSubscriber = 37;
        static final int TRANSACTION_enableDataConnectivity = 38;
        static final int TRANSACTION_enableLocationUpdates = 34;
        static final int TRANSACTION_enableLocationUpdatesForSubscriber = 35;
        static final int TRANSACTION_enableVideoCalling = 111;
        static final int TRANSACTION_endCall = 3;
        static final int TRANSACTION_endCallForSubscriber = 4;
        static final int TRANSACTION_factoryReset = 122;
        static final int TRANSACTION_getActivePhoneType = 49;
        static final int TRANSACTION_getActivePhoneTypeForSubscriber = 50;
        static final int TRANSACTION_getAllCellInfo = 70;
        static final int TRANSACTION_getCalculatedPreferredNetworkType = 83;
        static final int TRANSACTION_getCallState = 43;
        static final int TRANSACTION_getCallStateForSubscriber = 44;
        static final int TRANSACTION_getCarrierPackageNamesForIntentAndPhone = 99;
        static final int TRANSACTION_getCarrierPrivilegeStatus = 96;
        static final int TRANSACTION_getCdmaEriIconIndex = 51;
        static final int TRANSACTION_getCdmaEriIconIndexForSubscriber = 52;
        static final int TRANSACTION_getCdmaEriIconMode = 53;
        static final int TRANSACTION_getCdmaEriIconModeForSubscriber = 54;
        static final int TRANSACTION_getCdmaEriText = 55;
        static final int TRANSACTION_getCdmaEriTextForSubscriber = 56;
        static final int TRANSACTION_getCdmaMdn = 94;
        static final int TRANSACTION_getCdmaMin = 95;
        static final int TRANSACTION_getCellLocation = 41;
        static final int TRANSACTION_getCellNetworkScanResults = 87;
        static final int TRANSACTION_getDataActivity = 45;
        static final int TRANSACTION_getDataActivityForSubscriber = 46;
        static final int TRANSACTION_getDataEnabled = 91;
        static final int TRANSACTION_getDataNetworkType = 63;
        static final int TRANSACTION_getDataNetworkTypeForSubscriber = 64;
        static final int TRANSACTION_getDataState = 47;
        static final int TRANSACTION_getDataStateForSubscriber = 48;
        static final int TRANSACTION_getDefaultSim = 72;
        static final int TRANSACTION_getDeviceId = 120;
        static final int TRANSACTION_getIccAtr = 125;
        static final int TRANSACTION_getLine1AlphaTagForDisplay = 102;
        static final int TRANSACTION_getLine1NumberForDisplay = 101;
        static final int TRANSACTION_getLocaleFromDefaultSim = 123;
        static final int TRANSACTION_getLteOnCdmaMode = 68;
        static final int TRANSACTION_getLteOnCdmaModeForSubscriber = 69;
        static final int TRANSACTION_getMergedSubscriberIds = 103;
        static final int TRANSACTION_getModemActivityInfo = 124;
        static final int TRANSACTION_getNeighboringCellInfo = 42;
        static final int TRANSACTION_getNetworkType = 61;
        static final int TRANSACTION_getNetworkTypeForSubscriber = 62;
        static final int TRANSACTION_getPcscfAddress = 92;
        static final int TRANSACTION_getPreferredNetworkType = 84;
        static final int TRANSACTION_getRadioAccessFamily = 110;
        static final int TRANSACTION_getSubIdForPhoneAccount = 121;
        static final int TRANSACTION_getTetherApnRequired = 85;
        static final int TRANSACTION_getVoiceMessageCount = 59;
        static final int TRANSACTION_getVoiceMessageCountForSubscriber = 60;
        static final int TRANSACTION_getVoiceNetworkTypeForSubscriber = 65;
        static final int TRANSACTION_handlePinMmi = 25;
        static final int TRANSACTION_handlePinMmiForSubscriber = 26;
        static final int TRANSACTION_hasIccCard = 66;
        static final int TRANSACTION_hasIccCardUsingSlotId = 67;
        static final int TRANSACTION_iccCloseLogicalChannel = 74;
        static final int TRANSACTION_iccCloseLogicalChannelUsingSlot = 130;
        static final int TRANSACTION_iccExchangeSimIO = 77;
        static final int TRANSACTION_iccExchangeSimIOExUsingSlot = 128;
        static final int TRANSACTION_iccExchangeSimIOUsingSlot = 127;
        static final int TRANSACTION_iccOpenLogicalChannel = 73;
        static final int TRANSACTION_iccOpenLogicalChannelUsingSlot = 129;
        static final int TRANSACTION_iccOpenLogicalChannelWithSW = 126;
        static final int TRANSACTION_iccTransmitApduBasicChannel = 76;
        static final int TRANSACTION_iccTransmitApduBasicChannelUsingSlot = 132;
        static final int TRANSACTION_iccTransmitApduLogicalChannel = 75;
        static final int TRANSACTION_iccTransmitApduLogicalChannelUsingSlot = 131;
        static final int TRANSACTION_invokeOemRilRequestRaw = 106;
        static final int TRANSACTION_isDataConnectivityPossible = 40;
        static final int TRANSACTION_isHearingAidCompatibilitySupported = 116;
        static final int TRANSACTION_isIdle = 12;
        static final int TRANSACTION_isIdleForSubscriber = 13;
        static final int TRANSACTION_isImsRegistered = 117;
        static final int TRANSACTION_isOffhook = 8;
        static final int TRANSACTION_isOffhookForSubscriber = 9;
        static final int TRANSACTION_isRadioOn = 14;
        static final int TRANSACTION_isRadioOnForSubscriber = 15;
        static final int TRANSACTION_isRinging = 11;
        static final int TRANSACTION_isRingingForSubscriber = 10;
        static final int TRANSACTION_isSimPinEnabled = 16;
        static final int TRANSACTION_isTtyModeSupported = 115;
        static final int TRANSACTION_isVideoCallingEnabled = 112;
        static final int TRANSACTION_isVolteEnabled = 119;
        static final int TRANSACTION_isWifiCallingEnabled = 118;
        static final int TRANSACTION_isWorldPhone = 114;
        static final int TRANSACTION_needMobileRadioShutdown = 107;
        static final int TRANSACTION_needsOtaServiceProvisioning = 57;
        static final int TRANSACTION_nvReadItem = 79;
        static final int TRANSACTION_nvResetConfig = 82;
        static final int TRANSACTION_nvWriteCdmaPrl = 81;
        static final int TRANSACTION_nvWriteItem = 80;
        static final int TRANSACTION_sendEnvelopeWithStatus = 78;
        static final int TRANSACTION_setCellInfoListRate = 71;
        static final int TRANSACTION_setDataEnabled = 90;
        static final int TRANSACTION_setImsRegistrationState = 93;
        static final int TRANSACTION_setLine1NumberForDisplayForSubscriber = 100;
        static final int TRANSACTION_setNetworkSelectionModeAutomatic = 86;
        static final int TRANSACTION_setNetworkSelectionModeManual = 88;
        static final int TRANSACTION_setOperatorBrandOverride = 104;
        static final int TRANSACTION_setPolicyDataEnableForSubscriber = 133;
        static final int TRANSACTION_setPreferredNetworkType = 89;
        static final int TRANSACTION_setRadio = 29;
        static final int TRANSACTION_setRadioCapability = 109;
        static final int TRANSACTION_setRadioForSubscriber = 30;
        static final int TRANSACTION_setRadioPower = 31;
        static final int TRANSACTION_setRoamingOverride = 105;
        static final int TRANSACTION_setVoiceMailNumber = 58;
        static final int TRANSACTION_shutdownMobileRadios = 108;
        static final int TRANSACTION_silenceRinger = 7;
        static final int TRANSACTION_supplyPin = 17;
        static final int TRANSACTION_supplyPinForSubscriber = 18;
        static final int TRANSACTION_supplyPinReportResult = 21;
        static final int TRANSACTION_supplyPinReportResultForSubscriber = 22;
        static final int TRANSACTION_supplyPuk = 19;
        static final int TRANSACTION_supplyPukForSubscriber = 20;
        static final int TRANSACTION_supplyPukReportResult = 23;
        static final int TRANSACTION_supplyPukReportResultForSubscriber = 24;
        static final int TRANSACTION_toggleRadioOnOff = 27;
        static final int TRANSACTION_toggleRadioOnOffForSubscriber = 28;
        static final int TRANSACTION_updateServiceLocation = 32;
        static final int TRANSACTION_updateServiceLocationForSubscriber = 33;

        private static class Proxy implements ITelephony {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void dial(String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(number);
                    this.mRemote.transact(1, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void call(String callingPackage, String number) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    _data.writeString(number);
                    this.mRemote.transact(2, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean endCall() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(3, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean endCallForSubscriber(int subId) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(4, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void answerRingingCall() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(5, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void answerRingingCallForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(6, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void silenceRinger() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(7, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOffhook(String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(8, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isOffhookForSubscriber(int subId, String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(9, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRingingForSubscriber(int subId, String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(10, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRinging(String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(11, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIdle(String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(12, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isIdleForSubscriber(int subId, String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(13, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRadioOn(String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(14, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isRadioOnForSubscriber(int subId, String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(15, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isSimPinEnabled(String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(16, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supplyPin(String pin) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pin);
                    this.mRemote.transact(17, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supplyPinForSubscriber(int subId, String pin) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(pin);
                    this.mRemote.transact(18, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supplyPuk(String puk, String pin) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    this.mRemote.transact(19, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean supplyPukForSubscriber(int subId, String puk, String pin) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    this.mRemote.transact(20, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPinReportResult(String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pin);
                    this.mRemote.transact(21, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPinReportResultForSubscriber(int subId, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(pin);
                    this.mRemote.transact(22, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPukReportResult(String puk, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    this.mRemote.transact(23, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int[] supplyPukReportResultForSubscriber(int subId, String puk, String pin) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(puk);
                    _data.writeString(pin);
                    this.mRemote.transact(24, _data, _reply, 0);
                    _reply.readException();
                    int[] _result = _reply.createIntArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean handlePinMmi(String dialString) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(dialString);
                    this.mRemote.transact(25, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean handlePinMmiForSubscriber(int subId, String dialString) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(dialString);
                    this.mRemote.transact(26, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void toggleRadioOnOff() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(27, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void toggleRadioOnOffForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(28, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setRadio(boolean turnOn) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    int i;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (turnOn) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(29, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setRadioForSubscriber(int subId, boolean turnOn) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    int i;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (turnOn) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(30, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setRadioPower(boolean turnOn) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    int i;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (turnOn) {
                        i = 1;
                    } else {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(31, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateServiceLocation() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(32, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void updateServiceLocationForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(33, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableLocationUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(34, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableLocationUpdatesForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(35, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableLocationUpdates() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(36, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void disableLocationUpdatesForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(37, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean enableDataConnectivity() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(38, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean disableDataConnectivity() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(39, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isDataConnectivityPossible() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(40, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public Bundle getCellLocation(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    Bundle _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    this.mRemote.transact(41, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (Bundle) Bundle.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<NeighboringCellInfo> getNeighboringCellInfo(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    this.mRemote.transact(42, _data, _reply, 0);
                    _reply.readException();
                    List<NeighboringCellInfo> _result = _reply.createTypedArrayList(NeighboringCellInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCallState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(43, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCallStateForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(44, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataActivity() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(45, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataActivityForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(46, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataState() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(47, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataStateForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(48, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getActivePhoneType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(49, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getActivePhoneTypeForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(50, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconIndex(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(51, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconIndexForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(52, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconMode(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(53, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCdmaEriIconModeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(54, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaEriText(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(55, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaEriTextForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(56, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean needsOtaServiceProvisioning() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(57, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setVoiceMailNumber(int subId, String alphaTag, String number) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(alphaTag);
                    _data.writeString(number);
                    this.mRemote.transact(58, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoiceMessageCount() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(59, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoiceMessageCountForSubscriber(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(60, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNetworkType() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(61, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(62, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataNetworkType(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(63, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDataNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(64, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getVoiceNetworkTypeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(65, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasIccCard() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(66, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean hasIccCardUsingSlotId(int slotId) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    this.mRemote.transact(67, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLteOnCdmaMode(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(68, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getLteOnCdmaModeForSubscriber(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(69, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<CellInfo> getAllCellInfo(String callingPkg) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPkg);
                    this.mRemote.transact(70, _data, _reply, 0);
                    _reply.readException();
                    List<CellInfo> _result = _reply.createTypedArrayList(CellInfo.CREATOR);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setCellInfoListRate(int rateInMillis) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rateInMillis);
                    this.mRemote.transact(71, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getDefaultSim() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(72, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IccOpenLogicalChannelResponse iccOpenLogicalChannel(String AID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    IccOpenLogicalChannelResponse _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(AID);
                    this.mRemote.transact(73, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (IccOpenLogicalChannelResponse) IccOpenLogicalChannelResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean iccCloseLogicalChannel(int channel) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(channel);
                    this.mRemote.transact(74, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String iccTransmitApduLogicalChannel(int channel, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(channel);
                    _data.writeInt(cla);
                    _data.writeInt(instruction);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(data);
                    this.mRemote.transact(75, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String iccTransmitApduBasicChannel(int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cla);
                    _data.writeInt(instruction);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(data);
                    this.mRemote.transact(76, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] iccExchangeSimIO(int fileID, int command, int p1, int p2, int p3, String filePath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(fileID);
                    _data.writeInt(command);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(filePath);
                    this.mRemote.transact(77, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String sendEnvelopeWithStatus(String content) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(content);
                    this.mRemote.transact(78, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String nvReadItem(int itemID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(itemID);
                    this.mRemote.transact(79, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean nvWriteItem(int itemID, String itemValue) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(itemID);
                    _data.writeString(itemValue);
                    this.mRemote.transact(80, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean nvWriteCdmaPrl(byte[] preferredRoamingList) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(preferredRoamingList);
                    this.mRemote.transact(81, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean nvResetConfig(int resetType) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(resetType);
                    this.mRemote.transact(82, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCalculatedPreferredNetworkType(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(83, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getPreferredNetworkType(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(84, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getTetherApnRequired() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(85, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setNetworkSelectionModeAutomatic(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(86, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public CellNetworkScanResult getCellNetworkScanResults(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    CellNetworkScanResult _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(87, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (CellNetworkScanResult) CellNetworkScanResult.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setNetworkSelectionModeManual(int subId, OperatorInfo operator) throws RemoteException {
                boolean _result = true;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (operator != null) {
                        _data.writeInt(1);
                        operator.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(88, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() == 0) {
                        _result = false;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setPreferredNetworkType(int subId, int networkType) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeInt(networkType);
                    this.mRemote.transact(89, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setDataEnabled(int subId, boolean enable) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (enable) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(90, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getDataEnabled(int subId) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(91, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getPcscfAddress(String apnType, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(apnType);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(92, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setImsRegistrationState(boolean registered) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (registered) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(93, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaMdn(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(94, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getCdmaMin(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(95, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getCarrierPrivilegeStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(96, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkCarrierPrivilegesForPackage(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(97, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int checkCarrierPrivilegesForPackageAnyPhone(String pkgName) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(pkgName);
                    this.mRemote.transact(98, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int phoneId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (intent != null) {
                        _data.writeInt(1);
                        intent.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    _data.writeInt(phoneId);
                    this.mRemote.transact(99, _data, _reply, 0);
                    _reply.readException();
                    List<String> _result = _reply.createStringArrayList();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setLine1NumberForDisplayForSubscriber(int subId, String alphaTag, String number) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(alphaTag);
                    _data.writeString(number);
                    this.mRemote.transact(100, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getLine1NumberForDisplay(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(101, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getLine1AlphaTagForDisplay(int subId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(102, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String[] getMergedSubscriberIds(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(103, _data, _reply, 0);
                    _reply.readException();
                    String[] _result = _reply.createStringArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setOperatorBrandOverride(String brand) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(brand);
                    this.mRemote.transact(104, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean setRoamingOverride(List<String> gsmRoamingList, List<String> gsmNonRoamingList, List<String> cdmaRoamingList, List<String> cdmaNonRoamingList) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStringList(gsmRoamingList);
                    _data.writeStringList(gsmNonRoamingList);
                    _data.writeStringList(cdmaRoamingList);
                    _data.writeStringList(cdmaNonRoamingList);
                    this.mRemote.transact(105, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int invokeOemRilRequestRaw(byte[] oemReq, byte[] oemResp) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeByteArray(oemReq);
                    if (oemResp == null) {
                        _data.writeInt(-1);
                    } else {
                        _data.writeInt(oemResp.length);
                    }
                    this.mRemote.transact(106, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readByteArray(oemResp);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean needMobileRadioShutdown() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(107, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void shutdownMobileRadios() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(108, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setRadioCapability(RadioAccessFamily[] rafs) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeTypedArray(rafs, 0);
                    this.mRemote.transact(109, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getRadioAccessFamily(int phoneId, String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(phoneId);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(Stub.TRANSACTION_getRadioAccessFamily, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void enableVideoCalling(boolean enable) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (enable) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_enableVideoCalling, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVideoCallingEnabled(String callingPackage) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(112, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean canChangeDtmfToneLength() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_canChangeDtmfToneLength, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWorldPhone() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isWorldPhone, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isTtyModeSupported() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isTtyModeSupported, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isHearingAidCompatibilitySupported() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isHearingAidCompatibilitySupported, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isImsRegistered() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isImsRegistered, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isWifiCallingEnabled() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isWifiCallingEnabled, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean isVolteEnabled() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_isVolteEnabled, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getDeviceId(String callingPackage) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeString(callingPackage);
                    this.mRemote.transact(Stub.TRANSACTION_getDeviceId, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (phoneAccount != null) {
                        _data.writeInt(1);
                        phoneAccount.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_getSubIdForPhoneAccount, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void factoryReset(int subId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    this.mRemote.transact(Stub.TRANSACTION_factoryReset, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getLocaleFromDefaultSim() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getLocaleFromDefaultSim, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ModemActivityInfo getModemActivityInfo() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    ModemActivityInfo _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getModemActivityInfo, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (ModemActivityInfo) ModemActivityInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getIccAtr(int slotId) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    this.mRemote.transact(Stub.TRANSACTION_getIccAtr, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] iccOpenLogicalChannelWithSW(int slotId, String AID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeString(AID);
                    this.mRemote.transact(126, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] iccExchangeSimIOUsingSlot(int slotId, int fileID, int command, int p1, int p2, int p3, String filePath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(fileID);
                    _data.writeInt(command);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(filePath);
                    this.mRemote.transact(127, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public byte[] iccExchangeSimIOExUsingSlot(int slotId, int fileID, int command, int p1, int p2, int p3, String filePath, String data, String pin2) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(fileID);
                    _data.writeInt(command);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(filePath);
                    _data.writeString(data);
                    _data.writeString(pin2);
                    this.mRemote.transact(128, _data, _reply, 0);
                    _reply.readException();
                    byte[] _result = _reply.createByteArray();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public IccOpenLogicalChannelResponse iccOpenLogicalChannelUsingSlot(int slotId, String AID) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    IccOpenLogicalChannelResponse _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeString(AID);
                    this.mRemote.transact(Stub.TRANSACTION_iccOpenLogicalChannelUsingSlot, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (IccOpenLogicalChannelResponse) IccOpenLogicalChannelResponse.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean iccCloseLogicalChannelUsingSlot(int slotId, int channel) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(channel);
                    this.mRemote.transact(130, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String iccTransmitApduLogicalChannelUsingSlot(int slotId, int channel, int cla, int instruction, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(channel);
                    _data.writeInt(cla);
                    _data.writeInt(instruction);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(data);
                    this.mRemote.transact(Stub.TRANSACTION_iccTransmitApduLogicalChannelUsingSlot, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String iccTransmitApduBasicChannelUsingSlot(int slotId, int cla, int command, int p1, int p2, int p3, String data) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(slotId);
                    _data.writeInt(cla);
                    _data.writeInt(command);
                    _data.writeInt(p1);
                    _data.writeInt(p2);
                    _data.writeInt(p3);
                    _data.writeString(data);
                    this.mRemote.transact(Stub.TRANSACTION_iccTransmitApduBasicChannelUsingSlot, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void setPolicyDataEnableForSubscriber(int subId, boolean enabled) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(subId);
                    if (enabled) {
                        i = 1;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setPolicyDataEnableForSubscriber, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static ITelephony asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof ITelephony)) {
                return new Proxy(obj);
            }
            return (ITelephony) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _result;
            int[] _result2;
            int _result3;
            String _result4;
            IccOpenLogicalChannelResponse _result5;
            byte[] _result6;
            String[] _result7;
            switch (code) {
                case 1:
                    data.enforceInterface(DESCRIPTOR);
                    dial(data.readString());
                    reply.writeNoException();
                    return true;
                case 2:
                    data.enforceInterface(DESCRIPTOR);
                    call(data.readString(), data.readString());
                    reply.writeNoException();
                    return true;
                case 3:
                    data.enforceInterface(DESCRIPTOR);
                    _result = endCall();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 4:
                    data.enforceInterface(DESCRIPTOR);
                    _result = endCallForSubscriber(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 5:
                    data.enforceInterface(DESCRIPTOR);
                    answerRingingCall();
                    reply.writeNoException();
                    return true;
                case 6:
                    data.enforceInterface(DESCRIPTOR);
                    answerRingingCallForSubscriber(data.readInt());
                    reply.writeNoException();
                    return true;
                case 7:
                    data.enforceInterface(DESCRIPTOR);
                    silenceRinger();
                    reply.writeNoException();
                    return true;
                case 8:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isOffhook(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 9:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isOffhookForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 10:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isRingingForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 11:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isRinging(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 12:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isIdle(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 13:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isIdleForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 14:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isRadioOn(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 15:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isRadioOnForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 16:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isSimPinEnabled(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 17:
                    data.enforceInterface(DESCRIPTOR);
                    _result = supplyPin(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 18:
                    data.enforceInterface(DESCRIPTOR);
                    _result = supplyPinForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 19:
                    data.enforceInterface(DESCRIPTOR);
                    _result = supplyPuk(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 20:
                    data.enforceInterface(DESCRIPTOR);
                    _result = supplyPukForSubscriber(data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 21:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = supplyPinReportResult(data.readString());
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 22:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = supplyPinReportResultForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 23:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = supplyPukReportResult(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 24:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = supplyPukReportResultForSubscriber(data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeIntArray(_result2);
                    return true;
                case 25:
                    data.enforceInterface(DESCRIPTOR);
                    _result = handlePinMmi(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 26:
                    data.enforceInterface(DESCRIPTOR);
                    _result = handlePinMmiForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 27:
                    data.enforceInterface(DESCRIPTOR);
                    toggleRadioOnOff();
                    reply.writeNoException();
                    return true;
                case 28:
                    data.enforceInterface(DESCRIPTOR);
                    toggleRadioOnOffForSubscriber(data.readInt());
                    reply.writeNoException();
                    return true;
                case 29:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setRadio(data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 30:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setRadioForSubscriber(data.readInt(), data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 31:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setRadioPower(data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 32:
                    data.enforceInterface(DESCRIPTOR);
                    updateServiceLocation();
                    reply.writeNoException();
                    return true;
                case 33:
                    data.enforceInterface(DESCRIPTOR);
                    updateServiceLocationForSubscriber(data.readInt());
                    reply.writeNoException();
                    return true;
                case 34:
                    data.enforceInterface(DESCRIPTOR);
                    enableLocationUpdates();
                    reply.writeNoException();
                    return true;
                case 35:
                    data.enforceInterface(DESCRIPTOR);
                    enableLocationUpdatesForSubscriber(data.readInt());
                    reply.writeNoException();
                    return true;
                case 36:
                    data.enforceInterface(DESCRIPTOR);
                    disableLocationUpdates();
                    reply.writeNoException();
                    return true;
                case 37:
                    data.enforceInterface(DESCRIPTOR);
                    disableLocationUpdatesForSubscriber(data.readInt());
                    reply.writeNoException();
                    return true;
                case 38:
                    data.enforceInterface(DESCRIPTOR);
                    _result = enableDataConnectivity();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 39:
                    data.enforceInterface(DESCRIPTOR);
                    _result = disableDataConnectivity();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 40:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isDataConnectivityPossible();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 41:
                    data.enforceInterface(DESCRIPTOR);
                    Bundle _result8 = getCellLocation(data.readString());
                    reply.writeNoException();
                    if (_result8 != null) {
                        reply.writeInt(1);
                        _result8.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 42:
                    data.enforceInterface(DESCRIPTOR);
                    List<NeighboringCellInfo> _result9 = getNeighboringCellInfo(data.readString());
                    reply.writeNoException();
                    reply.writeTypedList(_result9);
                    return true;
                case 43:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCallState();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 44:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCallStateForSubscriber(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 45:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDataActivity();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 46:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDataActivityForSubscriber(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 47:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDataState();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 48:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDataStateForSubscriber(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 49:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getActivePhoneType();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 50:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getActivePhoneTypeForSubscriber(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 51:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCdmaEriIconIndex(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 52:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCdmaEriIconIndexForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 53:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCdmaEriIconMode(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 54:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCdmaEriIconModeForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 55:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getCdmaEriText(data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 56:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getCdmaEriTextForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 57:
                    data.enforceInterface(DESCRIPTOR);
                    _result = needsOtaServiceProvisioning();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 58:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setVoiceMailNumber(data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 59:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getVoiceMessageCount();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 60:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getVoiceMessageCountForSubscriber(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 61:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getNetworkType();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 62:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getNetworkTypeForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 63:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDataNetworkType(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 64:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDataNetworkTypeForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 65:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getVoiceNetworkTypeForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 66:
                    data.enforceInterface(DESCRIPTOR);
                    _result = hasIccCard();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 67:
                    data.enforceInterface(DESCRIPTOR);
                    _result = hasIccCardUsingSlotId(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 68:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getLteOnCdmaMode(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 69:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getLteOnCdmaModeForSubscriber(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 70:
                    data.enforceInterface(DESCRIPTOR);
                    List<CellInfo> _result10 = getAllCellInfo(data.readString());
                    reply.writeNoException();
                    reply.writeTypedList(_result10);
                    return true;
                case 71:
                    data.enforceInterface(DESCRIPTOR);
                    setCellInfoListRate(data.readInt());
                    reply.writeNoException();
                    return true;
                case 72:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getDefaultSim();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 73:
                    data.enforceInterface(DESCRIPTOR);
                    _result5 = iccOpenLogicalChannel(data.readString());
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 74:
                    data.enforceInterface(DESCRIPTOR);
                    _result = iccCloseLogicalChannel(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 75:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = iccTransmitApduLogicalChannel(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 76:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = iccTransmitApduBasicChannel(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 77:
                    data.enforceInterface(DESCRIPTOR);
                    _result6 = iccExchangeSimIO(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    return true;
                case 78:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = sendEnvelopeWithStatus(data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 79:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = nvReadItem(data.readInt());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 80:
                    data.enforceInterface(DESCRIPTOR);
                    _result = nvWriteItem(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 81:
                    data.enforceInterface(DESCRIPTOR);
                    _result = nvWriteCdmaPrl(data.createByteArray());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 82:
                    data.enforceInterface(DESCRIPTOR);
                    _result = nvResetConfig(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 83:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCalculatedPreferredNetworkType(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 84:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getPreferredNetworkType(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 85:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getTetherApnRequired();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 86:
                    data.enforceInterface(DESCRIPTOR);
                    setNetworkSelectionModeAutomatic(data.readInt());
                    reply.writeNoException();
                    return true;
                case 87:
                    data.enforceInterface(DESCRIPTOR);
                    CellNetworkScanResult _result11 = getCellNetworkScanResults(data.readInt());
                    reply.writeNoException();
                    if (_result11 != null) {
                        reply.writeInt(1);
                        _result11.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 88:
                    OperatorInfo _arg1;
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg1 = (OperatorInfo) OperatorInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg1 = null;
                    }
                    _result = setNetworkSelectionModeManual(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 89:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setPreferredNetworkType(data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 90:
                    data.enforceInterface(DESCRIPTOR);
                    setDataEnabled(data.readInt(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 91:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getDataEnabled(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 92:
                    data.enforceInterface(DESCRIPTOR);
                    _result7 = getPcscfAddress(data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeStringArray(_result7);
                    return true;
                case 93:
                    data.enforceInterface(DESCRIPTOR);
                    setImsRegistrationState(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 94:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getCdmaMdn(data.readInt());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 95:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getCdmaMin(data.readInt());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 96:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getCarrierPrivilegeStatus();
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 97:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = checkCarrierPrivilegesForPackage(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 98:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = checkCarrierPrivilegesForPackageAnyPhone(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case 99:
                    Intent _arg02;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = (Intent) Intent.CREATOR.createFromParcel(data);
                    } else {
                        _arg02 = null;
                    }
                    List<String> _result12 = getCarrierPackageNamesForIntentAndPhone(_arg02, data.readInt());
                    reply.writeNoException();
                    reply.writeStringList(_result12);
                    return true;
                case 100:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setLine1NumberForDisplayForSubscriber(data.readInt(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 101:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getLine1NumberForDisplay(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 102:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getLine1AlphaTagForDisplay(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 103:
                    data.enforceInterface(DESCRIPTOR);
                    _result7 = getMergedSubscriberIds(data.readString());
                    reply.writeNoException();
                    reply.writeStringArray(_result7);
                    return true;
                case 104:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setOperatorBrandOverride(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 105:
                    data.enforceInterface(DESCRIPTOR);
                    _result = setRoamingOverride(data.createStringArrayList(), data.createStringArrayList(), data.createStringArrayList(), data.createStringArrayList());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 106:
                    byte[] _arg12;
                    data.enforceInterface(DESCRIPTOR);
                    byte[] _arg03 = data.createByteArray();
                    int _arg1_length = data.readInt();
                    if (_arg1_length < 0) {
                        _arg12 = null;
                    } else {
                        _arg12 = new byte[_arg1_length];
                    }
                    _result3 = invokeOemRilRequestRaw(_arg03, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    reply.writeByteArray(_arg12);
                    return true;
                case 107:
                    data.enforceInterface(DESCRIPTOR);
                    _result = needMobileRadioShutdown();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case 108:
                    data.enforceInterface(DESCRIPTOR);
                    shutdownMobileRadios();
                    reply.writeNoException();
                    return true;
                case 109:
                    data.enforceInterface(DESCRIPTOR);
                    setRadioCapability((RadioAccessFamily[]) data.createTypedArray(RadioAccessFamily.CREATOR));
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getRadioAccessFamily /*110*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result3 = getRadioAccessFamily(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case TRANSACTION_enableVideoCalling /*111*/:
                    data.enforceInterface(DESCRIPTOR);
                    enableVideoCalling(data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 112:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isVideoCallingEnabled(data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_canChangeDtmfToneLength /*113*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = canChangeDtmfToneLength();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_isWorldPhone /*114*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isWorldPhone();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_isTtyModeSupported /*115*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isTtyModeSupported();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_isHearingAidCompatibilitySupported /*116*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isHearingAidCompatibilitySupported();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_isImsRegistered /*117*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isImsRegistered();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_isWifiCallingEnabled /*118*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isWifiCallingEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_isVolteEnabled /*119*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = isVolteEnabled();
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_getDeviceId /*120*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getDeviceId(data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case TRANSACTION_getSubIdForPhoneAccount /*121*/:
                    PhoneAccount _arg04;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg04 = (PhoneAccount) PhoneAccount.CREATOR.createFromParcel(data);
                    } else {
                        _arg04 = null;
                    }
                    _result3 = getSubIdForPhoneAccount(_arg04);
                    reply.writeNoException();
                    reply.writeInt(_result3);
                    return true;
                case TRANSACTION_factoryReset /*122*/:
                    data.enforceInterface(DESCRIPTOR);
                    factoryReset(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getLocaleFromDefaultSim /*123*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getLocaleFromDefaultSim();
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case TRANSACTION_getModemActivityInfo /*124*/:
                    data.enforceInterface(DESCRIPTOR);
                    ModemActivityInfo _result13 = getModemActivityInfo();
                    reply.writeNoException();
                    if (_result13 != null) {
                        reply.writeInt(1);
                        _result13.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case TRANSACTION_getIccAtr /*125*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = getIccAtr(data.readInt());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case 126:
                    data.enforceInterface(DESCRIPTOR);
                    _result6 = iccOpenLogicalChannelWithSW(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    return true;
                case 127:
                    data.enforceInterface(DESCRIPTOR);
                    _result6 = iccExchangeSimIOUsingSlot(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    return true;
                case 128:
                    data.enforceInterface(DESCRIPTOR);
                    _result6 = iccExchangeSimIOExUsingSlot(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString(), data.readString(), data.readString());
                    reply.writeNoException();
                    reply.writeByteArray(_result6);
                    return true;
                case TRANSACTION_iccOpenLogicalChannelUsingSlot /*129*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result5 = iccOpenLogicalChannelUsingSlot(data.readInt(), data.readString());
                    reply.writeNoException();
                    if (_result5 != null) {
                        reply.writeInt(1);
                        _result5.writeToParcel(reply, 1);
                    } else {
                        reply.writeInt(0);
                    }
                    return true;
                case 130:
                    data.enforceInterface(DESCRIPTOR);
                    _result = iccCloseLogicalChannelUsingSlot(data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result ? 1 : 0);
                    return true;
                case TRANSACTION_iccTransmitApduLogicalChannelUsingSlot /*131*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = iccTransmitApduLogicalChannelUsingSlot(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case TRANSACTION_iccTransmitApduBasicChannelUsingSlot /*132*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result4 = iccTransmitApduBasicChannelUsingSlot(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeString(_result4);
                    return true;
                case TRANSACTION_setPolicyDataEnableForSubscriber /*133*/:
                    data.enforceInterface(DESCRIPTOR);
                    setPolicyDataEnableForSubscriber(data.readInt(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void answerRingingCall() throws RemoteException;

    void answerRingingCallForSubscriber(int i) throws RemoteException;

    void call(String str, String str2) throws RemoteException;

    boolean canChangeDtmfToneLength() throws RemoteException;

    int checkCarrierPrivilegesForPackage(String str) throws RemoteException;

    int checkCarrierPrivilegesForPackageAnyPhone(String str) throws RemoteException;

    void dial(String str) throws RemoteException;

    boolean disableDataConnectivity() throws RemoteException;

    void disableLocationUpdates() throws RemoteException;

    void disableLocationUpdatesForSubscriber(int i) throws RemoteException;

    boolean enableDataConnectivity() throws RemoteException;

    void enableLocationUpdates() throws RemoteException;

    void enableLocationUpdatesForSubscriber(int i) throws RemoteException;

    void enableVideoCalling(boolean z) throws RemoteException;

    boolean endCall() throws RemoteException;

    boolean endCallForSubscriber(int i) throws RemoteException;

    void factoryReset(int i) throws RemoteException;

    int getActivePhoneType() throws RemoteException;

    int getActivePhoneTypeForSubscriber(int i) throws RemoteException;

    List<CellInfo> getAllCellInfo(String str) throws RemoteException;

    int getCalculatedPreferredNetworkType(String str) throws RemoteException;

    int getCallState() throws RemoteException;

    int getCallStateForSubscriber(int i) throws RemoteException;

    List<String> getCarrierPackageNamesForIntentAndPhone(Intent intent, int i) throws RemoteException;

    int getCarrierPrivilegeStatus() throws RemoteException;

    int getCdmaEriIconIndex(String str) throws RemoteException;

    int getCdmaEriIconIndexForSubscriber(int i, String str) throws RemoteException;

    int getCdmaEriIconMode(String str) throws RemoteException;

    int getCdmaEriIconModeForSubscriber(int i, String str) throws RemoteException;

    String getCdmaEriText(String str) throws RemoteException;

    String getCdmaEriTextForSubscriber(int i, String str) throws RemoteException;

    String getCdmaMdn(int i) throws RemoteException;

    String getCdmaMin(int i) throws RemoteException;

    Bundle getCellLocation(String str) throws RemoteException;

    CellNetworkScanResult getCellNetworkScanResults(int i) throws RemoteException;

    int getDataActivity() throws RemoteException;

    int getDataActivityForSubscriber(int i) throws RemoteException;

    boolean getDataEnabled(int i) throws RemoteException;

    int getDataNetworkType(String str) throws RemoteException;

    int getDataNetworkTypeForSubscriber(int i, String str) throws RemoteException;

    int getDataState() throws RemoteException;

    int getDataStateForSubscriber(int i) throws RemoteException;

    int getDefaultSim() throws RemoteException;

    String getDeviceId(String str) throws RemoteException;

    String getIccAtr(int i) throws RemoteException;

    String getLine1AlphaTagForDisplay(int i, String str) throws RemoteException;

    String getLine1NumberForDisplay(int i, String str) throws RemoteException;

    String getLocaleFromDefaultSim() throws RemoteException;

    int getLteOnCdmaMode(String str) throws RemoteException;

    int getLteOnCdmaModeForSubscriber(int i, String str) throws RemoteException;

    String[] getMergedSubscriberIds(String str) throws RemoteException;

    ModemActivityInfo getModemActivityInfo() throws RemoteException;

    List<NeighboringCellInfo> getNeighboringCellInfo(String str) throws RemoteException;

    int getNetworkType() throws RemoteException;

    int getNetworkTypeForSubscriber(int i, String str) throws RemoteException;

    String[] getPcscfAddress(String str, String str2) throws RemoteException;

    int getPreferredNetworkType(int i) throws RemoteException;

    int getRadioAccessFamily(int i, String str) throws RemoteException;

    int getSubIdForPhoneAccount(PhoneAccount phoneAccount) throws RemoteException;

    int getTetherApnRequired() throws RemoteException;

    int getVoiceMessageCount() throws RemoteException;

    int getVoiceMessageCountForSubscriber(int i) throws RemoteException;

    int getVoiceNetworkTypeForSubscriber(int i, String str) throws RemoteException;

    boolean handlePinMmi(String str) throws RemoteException;

    boolean handlePinMmiForSubscriber(int i, String str) throws RemoteException;

    boolean hasIccCard() throws RemoteException;

    boolean hasIccCardUsingSlotId(int i) throws RemoteException;

    boolean iccCloseLogicalChannel(int i) throws RemoteException;

    boolean iccCloseLogicalChannelUsingSlot(int i, int i2) throws RemoteException;

    byte[] iccExchangeSimIO(int i, int i2, int i3, int i4, int i5, String str) throws RemoteException;

    byte[] iccExchangeSimIOExUsingSlot(int i, int i2, int i3, int i4, int i5, int i6, String str, String str2, String str3) throws RemoteException;

    byte[] iccExchangeSimIOUsingSlot(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException;

    IccOpenLogicalChannelResponse iccOpenLogicalChannel(String str) throws RemoteException;

    IccOpenLogicalChannelResponse iccOpenLogicalChannelUsingSlot(int i, String str) throws RemoteException;

    byte[] iccOpenLogicalChannelWithSW(int i, String str) throws RemoteException;

    String iccTransmitApduBasicChannel(int i, int i2, int i3, int i4, int i5, String str) throws RemoteException;

    String iccTransmitApduBasicChannelUsingSlot(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException;

    String iccTransmitApduLogicalChannel(int i, int i2, int i3, int i4, int i5, int i6, String str) throws RemoteException;

    String iccTransmitApduLogicalChannelUsingSlot(int i, int i2, int i3, int i4, int i5, int i6, int i7, String str) throws RemoteException;

    int invokeOemRilRequestRaw(byte[] bArr, byte[] bArr2) throws RemoteException;

    boolean isDataConnectivityPossible() throws RemoteException;

    boolean isHearingAidCompatibilitySupported() throws RemoteException;

    boolean isIdle(String str) throws RemoteException;

    boolean isIdleForSubscriber(int i, String str) throws RemoteException;

    boolean isImsRegistered() throws RemoteException;

    boolean isOffhook(String str) throws RemoteException;

    boolean isOffhookForSubscriber(int i, String str) throws RemoteException;

    boolean isRadioOn(String str) throws RemoteException;

    boolean isRadioOnForSubscriber(int i, String str) throws RemoteException;

    boolean isRinging(String str) throws RemoteException;

    boolean isRingingForSubscriber(int i, String str) throws RemoteException;

    boolean isSimPinEnabled(String str) throws RemoteException;

    boolean isTtyModeSupported() throws RemoteException;

    boolean isVideoCallingEnabled(String str) throws RemoteException;

    boolean isVolteEnabled() throws RemoteException;

    boolean isWifiCallingEnabled() throws RemoteException;

    boolean isWorldPhone() throws RemoteException;

    boolean needMobileRadioShutdown() throws RemoteException;

    boolean needsOtaServiceProvisioning() throws RemoteException;

    String nvReadItem(int i) throws RemoteException;

    boolean nvResetConfig(int i) throws RemoteException;

    boolean nvWriteCdmaPrl(byte[] bArr) throws RemoteException;

    boolean nvWriteItem(int i, String str) throws RemoteException;

    String sendEnvelopeWithStatus(String str) throws RemoteException;

    void setCellInfoListRate(int i) throws RemoteException;

    void setDataEnabled(int i, boolean z) throws RemoteException;

    void setImsRegistrationState(boolean z) throws RemoteException;

    boolean setLine1NumberForDisplayForSubscriber(int i, String str, String str2) throws RemoteException;

    void setNetworkSelectionModeAutomatic(int i) throws RemoteException;

    boolean setNetworkSelectionModeManual(int i, OperatorInfo operatorInfo) throws RemoteException;

    boolean setOperatorBrandOverride(String str) throws RemoteException;

    void setPolicyDataEnableForSubscriber(int i, boolean z) throws RemoteException;

    boolean setPreferredNetworkType(int i, int i2) throws RemoteException;

    boolean setRadio(boolean z) throws RemoteException;

    void setRadioCapability(RadioAccessFamily[] radioAccessFamilyArr) throws RemoteException;

    boolean setRadioForSubscriber(int i, boolean z) throws RemoteException;

    boolean setRadioPower(boolean z) throws RemoteException;

    boolean setRoamingOverride(List<String> list, List<String> list2, List<String> list3, List<String> list4) throws RemoteException;

    boolean setVoiceMailNumber(int i, String str, String str2) throws RemoteException;

    void shutdownMobileRadios() throws RemoteException;

    void silenceRinger() throws RemoteException;

    boolean supplyPin(String str) throws RemoteException;

    boolean supplyPinForSubscriber(int i, String str) throws RemoteException;

    int[] supplyPinReportResult(String str) throws RemoteException;

    int[] supplyPinReportResultForSubscriber(int i, String str) throws RemoteException;

    boolean supplyPuk(String str, String str2) throws RemoteException;

    boolean supplyPukForSubscriber(int i, String str, String str2) throws RemoteException;

    int[] supplyPukReportResult(String str, String str2) throws RemoteException;

    int[] supplyPukReportResultForSubscriber(int i, String str, String str2) throws RemoteException;

    void toggleRadioOnOff() throws RemoteException;

    void toggleRadioOnOffForSubscriber(int i) throws RemoteException;

    void updateServiceLocation() throws RemoteException;

    void updateServiceLocationForSubscriber(int i) throws RemoteException;
}
