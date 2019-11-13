#import <Cordova/CDV.h>

@interface KawebCordovaPluginFileSpace : CDVPlugin

- (void)getFreeDiskSpace:(CDVInvokedUrlCommand*)command;

@end
