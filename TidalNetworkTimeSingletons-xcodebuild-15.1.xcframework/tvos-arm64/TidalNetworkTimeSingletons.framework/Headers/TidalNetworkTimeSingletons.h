#import <Foundation/NSArray.h>
#import <Foundation/NSDictionary.h>
#import <Foundation/NSError.h>
#import <Foundation/NSObject.h>
#import <Foundation/NSSet.h>
#import <Foundation/NSString.h>
#import <Foundation/NSValue.h>

@class TNTNTPServer, TNTSKotlinArray<T>, TNTSNTPClient, TNTProtocolFamily, TNTNTPVersion, TNTSKotlinCancellationException, TNTSKotlinThrowable, TNTSKotlinEnumCompanion, TNTSKotlinEnum<E>, TNTSKotlinException, TNTSKotlinRuntimeException, TNTSKotlinIllegalStateException, TNTSKotlinUnit;

@protocol TNTSKotlinx_coroutines_coreJob, TNTSKotlinIterator, TNTSKotlinx_coroutines_coreChildHandle, TNTSKotlinx_coroutines_coreChildJob, TNTSKotlinx_coroutines_coreDisposableHandle, TNTSKotlinSequence, TNTSKotlinx_coroutines_coreSelectClause0, TNTSKotlinCoroutineContextKey, TNTSKotlinCoroutineContextElement, TNTSKotlinCoroutineContext, TNTSKotlinComparable, TNTSKotlinx_coroutines_coreParentJob, TNTSKotlinx_coroutines_coreSelectInstance, TNTSKotlinx_coroutines_coreSelectClause;

NS_ASSUME_NONNULL_BEGIN
#pragma clang diagnostic push
#pragma clang diagnostic ignored "-Wunknown-warning-option"
#pragma clang diagnostic ignored "-Wincompatible-property-type"
#pragma clang diagnostic ignored "-Wnullability"

#pragma push_macro("_Nullable_result")
#if !__has_feature(nullability_nullable_result)
#undef _Nullable_result
#define _Nullable_result _Nullable
#endif

__attribute__((swift_name("KotlinBase")))
@interface TNTSBase : NSObject
- (instancetype)init __attribute__((unavailable));
+ (instancetype)new __attribute__((unavailable));
+ (void)initialize __attribute__((objc_requires_super));
@end

@interface TNTSBase (TNTSBaseCopying) <NSCopying>
@end

__attribute__((swift_name("KotlinMutableSet")))
@interface TNTSMutableSet<ObjectType> : NSMutableSet<ObjectType>
@end

__attribute__((swift_name("KotlinMutableDictionary")))
@interface TNTSMutableDictionary<KeyType, ObjectType> : NSMutableDictionary<KeyType, ObjectType>
@end

@interface NSError (NSErrorTNTSKotlinException)
@property (readonly) id _Nullable kotlinException;
@end

__attribute__((swift_name("KotlinNumber")))
@interface TNTSNumber : NSNumber
- (instancetype)initWithChar:(char)value __attribute__((unavailable));
- (instancetype)initWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
- (instancetype)initWithShort:(short)value __attribute__((unavailable));
- (instancetype)initWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
- (instancetype)initWithInt:(int)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
- (instancetype)initWithLong:(long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
- (instancetype)initWithLongLong:(long long)value __attribute__((unavailable));
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
- (instancetype)initWithFloat:(float)value __attribute__((unavailable));
- (instancetype)initWithDouble:(double)value __attribute__((unavailable));
- (instancetype)initWithBool:(BOOL)value __attribute__((unavailable));
- (instancetype)initWithInteger:(NSInteger)value __attribute__((unavailable));
- (instancetype)initWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
+ (instancetype)numberWithChar:(char)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedChar:(unsigned char)value __attribute__((unavailable));
+ (instancetype)numberWithShort:(short)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedShort:(unsigned short)value __attribute__((unavailable));
+ (instancetype)numberWithInt:(int)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInt:(unsigned int)value __attribute__((unavailable));
+ (instancetype)numberWithLong:(long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLong:(unsigned long)value __attribute__((unavailable));
+ (instancetype)numberWithLongLong:(long long)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value __attribute__((unavailable));
+ (instancetype)numberWithFloat:(float)value __attribute__((unavailable));
+ (instancetype)numberWithDouble:(double)value __attribute__((unavailable));
+ (instancetype)numberWithBool:(BOOL)value __attribute__((unavailable));
+ (instancetype)numberWithInteger:(NSInteger)value __attribute__((unavailable));
+ (instancetype)numberWithUnsignedInteger:(NSUInteger)value __attribute__((unavailable));
@end

__attribute__((swift_name("KotlinByte")))
@interface TNTSByte : TNTSNumber
- (instancetype)initWithChar:(char)value;
+ (instancetype)numberWithChar:(char)value;
@end

__attribute__((swift_name("KotlinUByte")))
@interface TNTSUByte : TNTSNumber
- (instancetype)initWithUnsignedChar:(unsigned char)value;
+ (instancetype)numberWithUnsignedChar:(unsigned char)value;
@end

__attribute__((swift_name("KotlinShort")))
@interface TNTSShort : TNTSNumber
- (instancetype)initWithShort:(short)value;
+ (instancetype)numberWithShort:(short)value;
@end

__attribute__((swift_name("KotlinUShort")))
@interface TNTSUShort : TNTSNumber
- (instancetype)initWithUnsignedShort:(unsigned short)value;
+ (instancetype)numberWithUnsignedShort:(unsigned short)value;
@end

__attribute__((swift_name("KotlinInt")))
@interface TNTSInt : TNTSNumber
- (instancetype)initWithInt:(int)value;
+ (instancetype)numberWithInt:(int)value;
@end

__attribute__((swift_name("KotlinUInt")))
@interface TNTSUInt : TNTSNumber
- (instancetype)initWithUnsignedInt:(unsigned int)value;
+ (instancetype)numberWithUnsignedInt:(unsigned int)value;
@end

__attribute__((swift_name("KotlinLong")))
@interface TNTSLong : TNTSNumber
- (instancetype)initWithLongLong:(long long)value;
+ (instancetype)numberWithLongLong:(long long)value;
@end

__attribute__((swift_name("KotlinULong")))
@interface TNTSULong : TNTSNumber
- (instancetype)initWithUnsignedLongLong:(unsigned long long)value;
+ (instancetype)numberWithUnsignedLongLong:(unsigned long long)value;
@end

__attribute__((swift_name("KotlinFloat")))
@interface TNTSFloat : TNTSNumber
- (instancetype)initWithFloat:(float)value;
+ (instancetype)numberWithFloat:(float)value;
@end

__attribute__((swift_name("KotlinDouble")))
@interface TNTSDouble : TNTSNumber
- (instancetype)initWithDouble:(double)value;
+ (instancetype)numberWithDouble:(double)value;
@end

__attribute__((swift_name("KotlinBoolean")))
@interface TNTSBoolean : TNTSNumber
- (instancetype)initWithBool:(BOOL)value;
+ (instancetype)numberWithBool:(BOOL)value;
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("SNTPClient")))
@interface TNTSNTPClient : TNTSBase
- (instancetype)initWithNtpServers:(TNTSKotlinArray<TNTNTPServer *> *)ntpServers synchronizationIntervalMs:(int64_t)synchronizationIntervalMs backupFilePath:(NSString * _Nullable)backupFilePath __attribute__((swift_name("init(ntpServers:synchronizationIntervalMs:backupFilePath:)"))) __attribute__((objc_designated_initializer));
- (id<TNTSKotlinx_coroutines_coreJob>)disableSynchronization __attribute__((swift_name("disableSynchronization()")));
- (id<TNTSKotlinx_coroutines_coreJob>)enableSynchronization __attribute__((swift_name("enableSynchronization()")));
@property (readonly) NSString * _Nullable backupFilePath __attribute__((swift_name("backupFilePath")));
@property (readonly) id _Nullable epochTimeMs __attribute__((swift_name("epochTimeMs")));
@property (readonly) TNTSKotlinArray<TNTNTPServer *> *ntpServers __attribute__((swift_name("ntpServers")));
@property (readonly) int64_t synchronizationInterval __attribute__((swift_name("synchronizationInterval")));
@end

@interface TNTSNTPClient (Extensions)
@property (readonly) TNTSNTPClient *singleton __attribute__((swift_name("singleton")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NTPServer")))
@interface TNTNTPServer : TNTSBase
- (instancetype)initWithHostName:(NSString *)hostName queryConnectTimeoutMs:(int64_t)queryConnectTimeoutMs queryReadTimeoutMs:(int64_t)queryReadTimeoutMs protocolFamilies:(TNTSKotlinArray<TNTProtocolFamily *> *)protocolFamilies queriesPerResolvedAddress:(int32_t)queriesPerResolvedAddress waitBetweenResolvedAddressQueriesMs:(int64_t)waitBetweenResolvedAddressQueriesMs ntpVersion:(TNTNTPVersion *)ntpVersion maxRootDelayMs:(int64_t)maxRootDelayMs maxRootDispersionMs:(int64_t)maxRootDispersionMs dnsResolutionTimeoutMs:(int64_t)dnsResolutionTimeoutMs __attribute__((swift_name("init(hostName:queryConnectTimeoutMs:queryReadTimeoutMs:protocolFamilies:queriesPerResolvedAddress:waitBetweenResolvedAddressQueriesMs:ntpVersion:maxRootDelayMs:maxRootDispersionMs:dnsResolutionTimeoutMs:)"))) __attribute__((objc_designated_initializer));
@property (readonly) int64_t dnsResolutionTimeout __attribute__((swift_name("dnsResolutionTimeout")));
@property (readonly) NSString *hostName __attribute__((swift_name("hostName")));
@property (readonly) int64_t maxRootDelay __attribute__((swift_name("maxRootDelay")));
@property (readonly) int64_t maxRootDispersion __attribute__((swift_name("maxRootDispersion")));
@property (readonly) TNTNTPVersion *ntpVersion __attribute__((swift_name("ntpVersion")));
@property (readonly) TNTSKotlinArray<TNTProtocolFamily *> *protocolFamilies __attribute__((swift_name("protocolFamilies")));
@property (readonly) int32_t queriesPerResolvedAddress __attribute__((swift_name("queriesPerResolvedAddress")));
@property (readonly) int64_t queryConnectTimeout __attribute__((swift_name("queryConnectTimeout")));
@property (readonly) int64_t queryReadTimeout __attribute__((swift_name("queryReadTimeout")));
@property (readonly) int64_t waitBetweenResolvedAddressQueries __attribute__((swift_name("waitBetweenResolvedAddressQueries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinArray")))
@interface TNTSKotlinArray<T> : TNTSBase
+ (instancetype)arrayWithSize:(int32_t)size init:(T _Nullable (^)(TNTSInt *))init __attribute__((swift_name("init(size:init:)")));
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (T _Nullable)getIndex:(int32_t)index __attribute__((swift_name("get(index:)")));
- (id<TNTSKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
- (void)setIndex:(int32_t)index value:(T _Nullable)value __attribute__((swift_name("set(index:value:)")));
@property (readonly) int32_t size __attribute__((swift_name("size")));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.3")
*/
__attribute__((swift_name("KotlinCoroutineContext")))
@protocol TNTSKotlinCoroutineContext
@required
- (id _Nullable)foldInitial:(id _Nullable)initial operation:(id _Nullable (^)(id _Nullable, id<TNTSKotlinCoroutineContextElement>))operation __attribute__((swift_name("fold(initial:operation:)")));
- (id<TNTSKotlinCoroutineContextElement> _Nullable)getKey:(id<TNTSKotlinCoroutineContextKey>)key __attribute__((swift_name("get(key:)")));
- (id<TNTSKotlinCoroutineContext>)minusKeyKey:(id<TNTSKotlinCoroutineContextKey>)key __attribute__((swift_name("minusKey(key:)")));
- (id<TNTSKotlinCoroutineContext>)plusContext:(id<TNTSKotlinCoroutineContext>)context __attribute__((swift_name("plus(context:)")));
@end

__attribute__((swift_name("KotlinCoroutineContextElement")))
@protocol TNTSKotlinCoroutineContextElement <TNTSKotlinCoroutineContext>
@required
@property (readonly) id<TNTSKotlinCoroutineContextKey> key __attribute__((swift_name("key")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreJob")))
@protocol TNTSKotlinx_coroutines_coreJob <TNTSKotlinCoroutineContextElement>
@required
- (id<TNTSKotlinx_coroutines_coreChildHandle>)attachChildChild:(id<TNTSKotlinx_coroutines_coreChildJob>)child __attribute__((swift_name("attachChild(child:)")));
- (void)cancelCause:(TNTSKotlinCancellationException * _Nullable)cause __attribute__((swift_name("cancel(cause:)")));
- (TNTSKotlinCancellationException *)getCancellationException __attribute__((swift_name("getCancellationException()")));
- (id<TNTSKotlinx_coroutines_coreDisposableHandle>)invokeOnCompletionHandler:(void (^)(TNTSKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(handler:)")));
- (id<TNTSKotlinx_coroutines_coreDisposableHandle>)invokeOnCompletionOnCancelling:(BOOL)onCancelling invokeImmediately:(BOOL)invokeImmediately handler:(void (^)(TNTSKotlinThrowable * _Nullable))handler __attribute__((swift_name("invokeOnCompletion(onCancelling:invokeImmediately:handler:)")));

/**
 * @note This method converts instances of CancellationException to errors.
 * Other uncaught Kotlin exceptions are fatal.
*/
- (void)joinWithCompletionHandler:(void (^)(NSError * _Nullable))completionHandler __attribute__((swift_name("join(completionHandler:)")));
- (id<TNTSKotlinx_coroutines_coreJob>)plusOther:(id<TNTSKotlinx_coroutines_coreJob>)other __attribute__((swift_name("plus(other:)"))) __attribute__((unavailable("Operator '+' on two Job objects is meaningless. Job is a coroutine context element and `+` is a set-sum operator for coroutine contexts. The job to the right of `+` just replaces the job the left of `+`.")));
- (BOOL)start __attribute__((swift_name("start()")));
@property (readonly) id<TNTSKotlinSequence> children __attribute__((swift_name("children")));
@property (readonly) BOOL isActive __attribute__((swift_name("isActive")));
@property (readonly) BOOL isCancelled __attribute__((swift_name("isCancelled")));
@property (readonly) BOOL isCompleted __attribute__((swift_name("isCompleted")));
@property (readonly) id<TNTSKotlinx_coroutines_coreSelectClause0> onJoin __attribute__((swift_name("onJoin")));

/**
 * @note annotations
 *   kotlinx.coroutines.ExperimentalCoroutinesApi
*/
@property (readonly) id<TNTSKotlinx_coroutines_coreJob> _Nullable parent __attribute__((swift_name("parent")));
@end

__attribute__((swift_name("KotlinComparable")))
@protocol TNTSKotlinComparable
@required
- (int32_t)compareToOther:(id _Nullable)other __attribute__((swift_name("compareTo(other:)")));
@end

__attribute__((swift_name("KotlinEnum")))
@interface TNTSKotlinEnum<E> : TNTSBase <TNTSKotlinComparable>
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer));
@property (class, readonly, getter=companion) TNTSKotlinEnumCompanion *companion __attribute__((swift_name("companion")));
- (int32_t)compareToOther:(E)other __attribute__((swift_name("compareTo(other:)")));
- (BOOL)isEqual:(id _Nullable)other __attribute__((swift_name("isEqual(_:)")));
- (NSUInteger)hash __attribute__((swift_name("hash()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) NSString *name __attribute__((swift_name("name")));
@property (readonly) int32_t ordinal __attribute__((swift_name("ordinal")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("ProtocolFamily")))
@interface TNTProtocolFamily : TNTSKotlinEnum<TNTProtocolFamily *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) TNTProtocolFamily *inet __attribute__((swift_name("inet")));
@property (class, readonly) TNTProtocolFamily *inet6 __attribute__((swift_name("inet6")));
+ (TNTSKotlinArray<TNTProtocolFamily *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<TNTProtocolFamily *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("NTPVersion")))
@interface TNTNTPVersion : TNTSKotlinEnum<TNTNTPVersion *>
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
- (instancetype)initWithName:(NSString *)name ordinal:(int32_t)ordinal __attribute__((swift_name("init(name:ordinal:)"))) __attribute__((objc_designated_initializer)) __attribute__((unavailable));
@property (class, readonly) TNTNTPVersion *zero __attribute__((swift_name("zero")));
@property (class, readonly) TNTNTPVersion *one __attribute__((swift_name("one")));
@property (class, readonly) TNTNTPVersion *two __attribute__((swift_name("two")));
@property (class, readonly) TNTNTPVersion *three __attribute__((swift_name("three")));
@property (class, readonly) TNTNTPVersion *four __attribute__((swift_name("four")));
+ (TNTSKotlinArray<TNTNTPVersion *> *)values __attribute__((swift_name("values()")));
@property (class, readonly) NSArray<TNTNTPVersion *> *entries __attribute__((swift_name("entries")));
@end

__attribute__((swift_name("KotlinIterator")))
@protocol TNTSKotlinIterator
@required
- (BOOL)hasNext __attribute__((swift_name("hasNext()")));
- (id _Nullable)next __attribute__((swift_name("next()")));
@end

__attribute__((swift_name("KotlinThrowable")))
@interface TNTSKotlinThrowable : TNTSBase
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));

/**
 * @note annotations
 *   kotlin.experimental.ExperimentalNativeApi
*/
- (TNTSKotlinArray<NSString *> *)getStackTrace __attribute__((swift_name("getStackTrace()")));
- (void)printStackTrace __attribute__((swift_name("printStackTrace()")));
- (NSString *)description __attribute__((swift_name("description()")));
@property (readonly) TNTSKotlinThrowable * _Nullable cause __attribute__((swift_name("cause")));
@property (readonly) NSString * _Nullable message __attribute__((swift_name("message")));
- (NSError *)asError __attribute__((swift_name("asError()")));
@end

__attribute__((swift_name("KotlinException")))
@interface TNTSKotlinException : TNTSKotlinThrowable
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("KotlinRuntimeException")))
@interface TNTSKotlinRuntimeException : TNTSKotlinException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("KotlinIllegalStateException")))
@interface TNTSKotlinIllegalStateException : TNTSKotlinRuntimeException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end


/**
 * @note annotations
 *   kotlin.SinceKotlin(version="1.4")
*/
__attribute__((swift_name("KotlinCancellationException")))
@interface TNTSKotlinCancellationException : TNTSKotlinIllegalStateException
- (instancetype)init __attribute__((swift_name("init()"))) __attribute__((objc_designated_initializer));
+ (instancetype)new __attribute__((availability(swift, unavailable, message="use object initializers instead")));
- (instancetype)initWithMessage:(NSString * _Nullable)message __attribute__((swift_name("init(message:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithCause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(cause:)"))) __attribute__((objc_designated_initializer));
- (instancetype)initWithMessage:(NSString * _Nullable)message cause:(TNTSKotlinThrowable * _Nullable)cause __attribute__((swift_name("init(message:cause:)"))) __attribute__((objc_designated_initializer));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreDisposableHandle")))
@protocol TNTSKotlinx_coroutines_coreDisposableHandle
@required
- (void)dispose __attribute__((swift_name("dispose()")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreChildHandle")))
@protocol TNTSKotlinx_coroutines_coreChildHandle <TNTSKotlinx_coroutines_coreDisposableHandle>
@required
- (BOOL)childCancelledCause:(TNTSKotlinThrowable *)cause __attribute__((swift_name("childCancelled(cause:)")));
@property (readonly) id<TNTSKotlinx_coroutines_coreJob> _Nullable parent __attribute__((swift_name("parent")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreChildJob")))
@protocol TNTSKotlinx_coroutines_coreChildJob <TNTSKotlinx_coroutines_coreJob>
@required
- (void)parentCancelledParentJob:(id<TNTSKotlinx_coroutines_coreParentJob>)parentJob __attribute__((swift_name("parentCancelled(parentJob:)")));
@end

__attribute__((swift_name("KotlinSequence")))
@protocol TNTSKotlinSequence
@required
- (id<TNTSKotlinIterator>)iterator __attribute__((swift_name("iterator()")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSelectClause")))
@protocol TNTSKotlinx_coroutines_coreSelectClause
@required
@property (readonly) id clauseObject __attribute__((swift_name("clauseObject")));
@property (readonly) TNTSKotlinUnit *(^(^ _Nullable onCancellationConstructor)(id<TNTSKotlinx_coroutines_coreSelectInstance>, id _Nullable, id _Nullable))(TNTSKotlinThrowable *) __attribute__((swift_name("onCancellationConstructor")));
@property (readonly) id _Nullable (^processResFunc)(id, id _Nullable, id _Nullable) __attribute__((swift_name("processResFunc")));
@property (readonly) void (^regFunc)(id, id<TNTSKotlinx_coroutines_coreSelectInstance>, id _Nullable) __attribute__((swift_name("regFunc")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSelectClause0")))
@protocol TNTSKotlinx_coroutines_coreSelectClause0 <TNTSKotlinx_coroutines_coreSelectClause>
@required
@end

__attribute__((swift_name("KotlinCoroutineContextKey")))
@protocol TNTSKotlinCoroutineContextKey
@required
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinEnumCompanion")))
@interface TNTSKotlinEnumCompanion : TNTSBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)companion __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) TNTSKotlinEnumCompanion *shared __attribute__((swift_name("shared")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreParentJob")))
@protocol TNTSKotlinx_coroutines_coreParentJob <TNTSKotlinx_coroutines_coreJob>
@required
- (TNTSKotlinCancellationException *)getChildJobCancellationCause __attribute__((swift_name("getChildJobCancellationCause()")));
@end

__attribute__((objc_subclassing_restricted))
__attribute__((swift_name("KotlinUnit")))
@interface TNTSKotlinUnit : TNTSBase
+ (instancetype)alloc __attribute__((unavailable));
+ (instancetype)allocWithZone:(struct _NSZone *)zone __attribute__((unavailable));
+ (instancetype)unit __attribute__((swift_name("init()")));
@property (class, readonly, getter=shared) TNTSKotlinUnit *shared __attribute__((swift_name("shared")));
- (NSString *)description __attribute__((swift_name("description()")));
@end

__attribute__((swift_name("Kotlinx_coroutines_coreSelectInstance")))
@protocol TNTSKotlinx_coroutines_coreSelectInstance
@required
- (void)disposeOnCompletionDisposableHandle:(id<TNTSKotlinx_coroutines_coreDisposableHandle>)disposableHandle __attribute__((swift_name("disposeOnCompletion(disposableHandle:)")));
- (void)selectInRegistrationPhaseInternalResult:(id _Nullable)internalResult __attribute__((swift_name("selectInRegistrationPhase(internalResult:)")));
- (BOOL)trySelectClauseObject:(id)clauseObject result:(id _Nullable)result __attribute__((swift_name("trySelect(clauseObject:result:)")));
@property (readonly) id<TNTSKotlinCoroutineContext> context __attribute__((swift_name("context")));
@end

#pragma pop_macro("_Nullable_result")
#pragma clang diagnostic pop
NS_ASSUME_NONNULL_END
