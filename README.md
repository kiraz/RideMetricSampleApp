# RideMetricSampleApp
This is a sample app uses RideMetric SDK and can be expanded to include specific functionality and features.

# RideMetric SDK
## Changelog
Version 4.2.0
* Fixed the crash related to transmission of large blocks of data by upgrading third party package
* Include referenceId for the trips that do not have GPS locations

Version 4.1.49
* Restoring ACRA settings to the previous configuration

Version 4.1.48
* Improved scoring
* Extended purging policy - deleting the trip after synchronizing it with the cloud server
* Fixed referenceId recording
* Removed log messages
* Expedited completion of stopTrip method when GPS is not available
* Added referenceId for the unifinished trips (e.g. trips interrupted by the phone reboot)

Version 4.1.46
* Debug version

Version 4.1.45
* Purge policy update

Version 4.1.43
* Minor bug fixes

Version 4.1.40
* Minor bug fixes

Version 4.1.39
* Faster invocation of connect listeners upon completion of connect procedure

Version 4.1.38
* Added a method getReferenceId providing a reference if of the latest started trip and reduced the latency of connect method

Version 4.1.35
* Imporoved and reduced the latency of the connect

Version 4.1.32
* Update Google Play Services library to 8.4.0

Version 4.1.27
* RideMetric.startTrip can accept reference id as parameter: startTrip(String referenceId)
* RideMetric.setMaxPermittedSpeed signature change: setMaxPermittedSpeed(Context context, int speed)


## Instalation
RideMetric SDK is installed as Gradle plugin.
```
compile 'com.ridemetric:ridemetric-controlsdk:4.1.46'
```

## API Reference
<https://ridemetric.github.io/RideMetricSampleApp/index.html>
