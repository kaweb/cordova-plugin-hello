#import "KawebCordovaPluginFileSpace.h"

@implementation KawebCordovaPluginFileSpace

  /**
   * Get the Free Disk Space in bytes
   */
- (NSNumber*)getFreeDiskSpace:(NSString*)appPath
{
    NSURL *url = [[NSURL alloc] initFileURLWithPath:NSHomeDirectory()];
    NSNumber* pNumAvail = (NSNumber*) [url resourceValuesForKeys:@[NSURLVolumeAvailableCapacityForImportantUsageKey] error:nil][NSURLVolumeAvailableCapacityForImportantUsageKey];
    
    NSNumber* pNumAvailRegular = (NSNumber*) [url resourceValuesForKeys:@[NSURLVolumeAvailableCapacityKey] error:nil][NSURLVolumeAvailableCapacityKey];
    NSNumber* pNumAvailOpportu = (NSNumber*) [url resourceValuesForKeys:@[NSURLVolumeAvailableCapacityForOpportunisticUsageKey] error:nil][NSURLVolumeAvailableCapacityForOpportunisticUsageKey];

    NSLog(@"Disk Space Available [ImportantUsage]: %@", pNumAvail);
    //NSLog(@"Disk Space Available [regular]: %@", pNumAvailRegular);
   // NSLog(@"Disk Space Available [Opportunistic]: %@", pNumAvailOpportu);
 
    return pNumAvail;
}

@end
