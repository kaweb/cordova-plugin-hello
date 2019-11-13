#import <Cordova/CDV.h>
#import "KawebCordovaPluginFileSpace.h"
#import "CDVLocalFilesystem.h"
#import "CDVAssetLibraryFilesystem.h"
#import <objc/message.h>

@implementation KawebCordovaPluginFileSpace

  /**
   * Get the Free Disk Space in bytes
   */
- (void)getFreeDiskSpace:(CDVInvokedUrlCommand*)command
{
    NSURL *url = [[NSURL alloc] initFileURLWithPath:NSHomeDirectory()];
    NSNumber* pNumAvail = (NSNumber*) [url resourceValuesForKeys:@[NSURLVolumeAvailableCapacityForImportantUsageKey] error:nil][NSURLVolumeAvailableCapacityForImportantUsageKey];
    
    NSNumber* pNumAvailRegular = (NSNumber*) [url resourceValuesForKeys:@[NSURLVolumeAvailableCapacityKey] error:nil][NSURLVolumeAvailableCapacityKey];
    NSNumber* pNumAvailOpportu = (NSNumber*) [url resourceValuesForKeys:@[NSURLVolumeAvailableCapacityForOpportunisticUsageKey] error:nil][NSURLVolumeAvailableCapacityForOpportunisticUsageKey];

    NSLog(@"Disk Space Available [ImportantUsage]: %@", pNumAvail);
    //NSLog(@"Disk Space Available [regular]: %@", pNumAvailRegular);
   // NSLog(@"Disk Space Available [Opportunistic]: %@", pNumAvailOpportu);
 
    NSString* strFreeSpace = [NSString stringWithFormat:@"%qu", [pNumAvail unsignedLongLongValue]];
  
    CDVPluginResult* result = [CDVPluginResult resultWithStatus:CDVCommandStatus_OK messageAsString:strFreeSpace];

    [self.commandDelegate sendPluginResult:result callbackId:command.callbackId];
}

@end
