
gradle assemble 

zipalign -p 4 app/build/outputs/apk/release/app-release-unsigned.apk release/MEWconnect-Android-unsigned.apk
