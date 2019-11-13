#import <Cordova/CDV.h>
#import <Foundation/Foundation.h>
#import <Cordova/CDVPlugin.h>

@interface KawebCordovaPluginFileSpace : CDVPlugin

- (void)getFreeDiskSpace:(CDVInvokedUrlCommand*)command;

@end
