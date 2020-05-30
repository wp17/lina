// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: G2GMsgs.proto

package com.github.wp17.lina.proto.msg;

public final class G2GProto {
  private G2GProto() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  public interface G2GExpireCacheReqOrBuilder extends
      // @@protoc_insertion_point(interface_extends:G2GExpireCacheReq)
      com.google.protobuf.MessageOrBuilder {

    /**
     * <code>string cacheKey = 1;</code>
     */
    java.lang.String getCacheKey();
    /**
     * <code>string cacheKey = 1;</code>
     */
    com.google.protobuf.ByteString
        getCacheKeyBytes();
  }
  /**
   * <pre>
   *广播缓存过期消息
   * </pre>
   *
   * Protobuf type {@code G2GExpireCacheReq}
   */
  public  static final class G2GExpireCacheReq extends
      com.google.protobuf.GeneratedMessageV3 implements
      // @@protoc_insertion_point(message_implements:G2GExpireCacheReq)
      G2GExpireCacheReqOrBuilder {
  private static final long serialVersionUID = 0L;
    // Use G2GExpireCacheReq.newBuilder() to construct.
    private G2GExpireCacheReq(com.google.protobuf.GeneratedMessageV3.Builder<?> builder) {
      super(builder);
    }
    private G2GExpireCacheReq() {
      cacheKey_ = "";
    }

    @java.lang.Override
    public final com.google.protobuf.UnknownFieldSet
    getUnknownFields() {
      return this.unknownFields;
    }
    private G2GExpireCacheReq(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      this();
      if (extensionRegistry == null) {
        throw new java.lang.NullPointerException();
      }
      int mutable_bitField0_ = 0;
      com.google.protobuf.UnknownFieldSet.Builder unknownFields =
          com.google.protobuf.UnknownFieldSet.newBuilder();
      try {
        boolean done = false;
        while (!done) {
          int tag = input.readTag();
          switch (tag) {
            case 0:
              done = true;
              break;
            default: {
              if (!parseUnknownFieldProto3(
                  input, unknownFields, extensionRegistry, tag)) {
                done = true;
              }
              break;
            }
            case 10: {
              java.lang.String s = input.readStringRequireUtf8();

              cacheKey_ = s;
              break;
            }
          }
        }
      } catch (com.google.protobuf.InvalidProtocolBufferException e) {
        throw e.setUnfinishedMessage(this);
      } catch (java.io.IOException e) {
        throw new com.google.protobuf.InvalidProtocolBufferException(
            e).setUnfinishedMessage(this);
      } finally {
        this.unknownFields = unknownFields.build();
        makeExtensionsImmutable();
      }
    }
    public static final com.google.protobuf.Descriptors.Descriptor
        getDescriptor() {
      return com.github.wp17.lina.proto.msg.G2GProto.internal_static_G2GExpireCacheReq_descriptor;
    }

    protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
        internalGetFieldAccessorTable() {
      return com.github.wp17.lina.proto.msg.G2GProto.internal_static_G2GExpireCacheReq_fieldAccessorTable
          .ensureFieldAccessorsInitialized(
              com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.class, com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.Builder.class);
    }

    public static final int CACHEKEY_FIELD_NUMBER = 1;
    private volatile java.lang.Object cacheKey_;
    /**
     * <code>string cacheKey = 1;</code>
     */
    public java.lang.String getCacheKey() {
      java.lang.Object ref = cacheKey_;
      if (ref instanceof java.lang.String) {
        return (java.lang.String) ref;
      } else {
        com.google.protobuf.ByteString bs = 
            (com.google.protobuf.ByteString) ref;
        java.lang.String s = bs.toStringUtf8();
        cacheKey_ = s;
        return s;
      }
    }
    /**
     * <code>string cacheKey = 1;</code>
     */
    public com.google.protobuf.ByteString
        getCacheKeyBytes() {
      java.lang.Object ref = cacheKey_;
      if (ref instanceof java.lang.String) {
        com.google.protobuf.ByteString b = 
            com.google.protobuf.ByteString.copyFromUtf8(
                (java.lang.String) ref);
        cacheKey_ = b;
        return b;
      } else {
        return (com.google.protobuf.ByteString) ref;
      }
    }

    private byte memoizedIsInitialized = -1;
    public final boolean isInitialized() {
      byte isInitialized = memoizedIsInitialized;
      if (isInitialized == 1) return true;
      if (isInitialized == 0) return false;

      memoizedIsInitialized = 1;
      return true;
    }

    public void writeTo(com.google.protobuf.CodedOutputStream output)
                        throws java.io.IOException {
      if (!getCacheKeyBytes().isEmpty()) {
        com.google.protobuf.GeneratedMessageV3.writeString(output, 1, cacheKey_);
      }
      unknownFields.writeTo(output);
    }

    public int getSerializedSize() {
      int size = memoizedSize;
      if (size != -1) return size;

      size = 0;
      if (!getCacheKeyBytes().isEmpty()) {
        size += com.google.protobuf.GeneratedMessageV3.computeStringSize(1, cacheKey_);
      }
      size += unknownFields.getSerializedSize();
      memoizedSize = size;
      return size;
    }

    @java.lang.Override
    public boolean equals(final java.lang.Object obj) {
      if (obj == this) {
       return true;
      }
      if (!(obj instanceof com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq)) {
        return super.equals(obj);
      }
      com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq other = (com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq) obj;

      boolean result = true;
      result = result && getCacheKey()
          .equals(other.getCacheKey());
      result = result && unknownFields.equals(other.unknownFields);
      return result;
    }

    @java.lang.Override
    public int hashCode() {
      if (memoizedHashCode != 0) {
        return memoizedHashCode;
      }
      int hash = 41;
      hash = (19 * hash) + getDescriptor().hashCode();
      hash = (37 * hash) + CACHEKEY_FIELD_NUMBER;
      hash = (53 * hash) + getCacheKey().hashCode();
      hash = (29 * hash) + unknownFields.hashCode();
      memoizedHashCode = hash;
      return hash;
    }

    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        java.nio.ByteBuffer data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        java.nio.ByteBuffer data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        com.google.protobuf.ByteString data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        com.google.protobuf.ByteString data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(byte[] data)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        byte[] data,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws com.google.protobuf.InvalidProtocolBufferException {
      return PARSER.parseFrom(data, extensionRegistry);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseDelimitedFrom(java.io.InputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseDelimitedFrom(
        java.io.InputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseDelimitedWithIOException(PARSER, input, extensionRegistry);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        com.google.protobuf.CodedInputStream input)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input);
    }
    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parseFrom(
        com.google.protobuf.CodedInputStream input,
        com.google.protobuf.ExtensionRegistryLite extensionRegistry)
        throws java.io.IOException {
      return com.google.protobuf.GeneratedMessageV3
          .parseWithIOException(PARSER, input, extensionRegistry);
    }

    public Builder newBuilderForType() { return newBuilder(); }
    public static Builder newBuilder() {
      return DEFAULT_INSTANCE.toBuilder();
    }
    public static Builder newBuilder(com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq prototype) {
      return DEFAULT_INSTANCE.toBuilder().mergeFrom(prototype);
    }
    public Builder toBuilder() {
      return this == DEFAULT_INSTANCE
          ? new Builder() : new Builder().mergeFrom(this);
    }

    @java.lang.Override
    protected Builder newBuilderForType(
        com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
      Builder builder = new Builder(parent);
      return builder;
    }
    /**
     * <pre>
     *广播缓存过期消息
     * </pre>
     *
     * Protobuf type {@code G2GExpireCacheReq}
     */
    public static final class Builder extends
        com.google.protobuf.GeneratedMessageV3.Builder<Builder> implements
        // @@protoc_insertion_point(builder_implements:G2GExpireCacheReq)
        com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReqOrBuilder {
      public static final com.google.protobuf.Descriptors.Descriptor
          getDescriptor() {
        return com.github.wp17.lina.proto.msg.G2GProto.internal_static_G2GExpireCacheReq_descriptor;
      }

      protected com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
          internalGetFieldAccessorTable() {
        return com.github.wp17.lina.proto.msg.G2GProto.internal_static_G2GExpireCacheReq_fieldAccessorTable
            .ensureFieldAccessorsInitialized(
                com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.class, com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.Builder.class);
      }

      // Construct using com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.newBuilder()
      private Builder() {
        maybeForceBuilderInitialization();
      }

      private Builder(
          com.google.protobuf.GeneratedMessageV3.BuilderParent parent) {
        super(parent);
        maybeForceBuilderInitialization();
      }
      private void maybeForceBuilderInitialization() {
        if (com.google.protobuf.GeneratedMessageV3
                .alwaysUseFieldBuilders) {
        }
      }
      public Builder clear() {
        super.clear();
        cacheKey_ = "";

        return this;
      }

      public com.google.protobuf.Descriptors.Descriptor
          getDescriptorForType() {
        return com.github.wp17.lina.proto.msg.G2GProto.internal_static_G2GExpireCacheReq_descriptor;
      }

      public com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq getDefaultInstanceForType() {
        return com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.getDefaultInstance();
      }

      public com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq build() {
        com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq result = buildPartial();
        if (!result.isInitialized()) {
          throw newUninitializedMessageException(result);
        }
        return result;
      }

      public com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq buildPartial() {
        com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq result = new com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq(this);
        result.cacheKey_ = cacheKey_;
        onBuilt();
        return result;
      }

      public Builder clone() {
        return (Builder) super.clone();
      }
      public Builder setField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.setField(field, value);
      }
      public Builder clearField(
          com.google.protobuf.Descriptors.FieldDescriptor field) {
        return (Builder) super.clearField(field);
      }
      public Builder clearOneof(
          com.google.protobuf.Descriptors.OneofDescriptor oneof) {
        return (Builder) super.clearOneof(oneof);
      }
      public Builder setRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          int index, java.lang.Object value) {
        return (Builder) super.setRepeatedField(field, index, value);
      }
      public Builder addRepeatedField(
          com.google.protobuf.Descriptors.FieldDescriptor field,
          java.lang.Object value) {
        return (Builder) super.addRepeatedField(field, value);
      }
      public Builder mergeFrom(com.google.protobuf.Message other) {
        if (other instanceof com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq) {
          return mergeFrom((com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq)other);
        } else {
          super.mergeFrom(other);
          return this;
        }
      }

      public Builder mergeFrom(com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq other) {
        if (other == com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq.getDefaultInstance()) return this;
        if (!other.getCacheKey().isEmpty()) {
          cacheKey_ = other.cacheKey_;
          onChanged();
        }
        this.mergeUnknownFields(other.unknownFields);
        onChanged();
        return this;
      }

      public final boolean isInitialized() {
        return true;
      }

      public Builder mergeFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws java.io.IOException {
        com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq parsedMessage = null;
        try {
          parsedMessage = PARSER.parsePartialFrom(input, extensionRegistry);
        } catch (com.google.protobuf.InvalidProtocolBufferException e) {
          parsedMessage = (com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq) e.getUnfinishedMessage();
          throw e.unwrapIOException();
        } finally {
          if (parsedMessage != null) {
            mergeFrom(parsedMessage);
          }
        }
        return this;
      }

      private java.lang.Object cacheKey_ = "";
      /**
       * <code>string cacheKey = 1;</code>
       */
      public java.lang.String getCacheKey() {
        java.lang.Object ref = cacheKey_;
        if (!(ref instanceof java.lang.String)) {
          com.google.protobuf.ByteString bs =
              (com.google.protobuf.ByteString) ref;
          java.lang.String s = bs.toStringUtf8();
          cacheKey_ = s;
          return s;
        } else {
          return (java.lang.String) ref;
        }
      }
      /**
       * <code>string cacheKey = 1;</code>
       */
      public com.google.protobuf.ByteString
          getCacheKeyBytes() {
        java.lang.Object ref = cacheKey_;
        if (ref instanceof String) {
          com.google.protobuf.ByteString b = 
              com.google.protobuf.ByteString.copyFromUtf8(
                  (java.lang.String) ref);
          cacheKey_ = b;
          return b;
        } else {
          return (com.google.protobuf.ByteString) ref;
        }
      }
      /**
       * <code>string cacheKey = 1;</code>
       */
      public Builder setCacheKey(
          java.lang.String value) {
        if (value == null) {
    throw new NullPointerException();
  }
  
        cacheKey_ = value;
        onChanged();
        return this;
      }
      /**
       * <code>string cacheKey = 1;</code>
       */
      public Builder clearCacheKey() {
        
        cacheKey_ = getDefaultInstance().getCacheKey();
        onChanged();
        return this;
      }
      /**
       * <code>string cacheKey = 1;</code>
       */
      public Builder setCacheKeyBytes(
          com.google.protobuf.ByteString value) {
        if (value == null) {
    throw new NullPointerException();
  }
  checkByteStringIsUtf8(value);
        
        cacheKey_ = value;
        onChanged();
        return this;
      }
      public final Builder setUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.setUnknownFieldsProto3(unknownFields);
      }

      public final Builder mergeUnknownFields(
          final com.google.protobuf.UnknownFieldSet unknownFields) {
        return super.mergeUnknownFields(unknownFields);
      }


      // @@protoc_insertion_point(builder_scope:G2GExpireCacheReq)
    }

    // @@protoc_insertion_point(class_scope:G2GExpireCacheReq)
    private static final com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq DEFAULT_INSTANCE;
    static {
      DEFAULT_INSTANCE = new com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq();
    }

    public static com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq getDefaultInstance() {
      return DEFAULT_INSTANCE;
    }

    private static final com.google.protobuf.Parser<G2GExpireCacheReq>
        PARSER = new com.google.protobuf.AbstractParser<G2GExpireCacheReq>() {
      public G2GExpireCacheReq parsePartialFrom(
          com.google.protobuf.CodedInputStream input,
          com.google.protobuf.ExtensionRegistryLite extensionRegistry)
          throws com.google.protobuf.InvalidProtocolBufferException {
        return new G2GExpireCacheReq(input, extensionRegistry);
      }
    };

    public static com.google.protobuf.Parser<G2GExpireCacheReq> parser() {
      return PARSER;
    }

    @java.lang.Override
    public com.google.protobuf.Parser<G2GExpireCacheReq> getParserForType() {
      return PARSER;
    }

    public com.github.wp17.lina.proto.msg.G2GProto.G2GExpireCacheReq getDefaultInstanceForType() {
      return DEFAULT_INSTANCE;
    }

  }

  private static final com.google.protobuf.Descriptors.Descriptor
    internal_static_G2GExpireCacheReq_descriptor;
  private static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_G2GExpireCacheReq_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\rG2GMsgs.proto\032\roptions.proto\",\n\021G2GExp" +
      "ireCacheReq\022\020\n\010cacheKey\030\001 \001(\t:\005\230\202\031\271\027B*\n\036" +
      "com.github.wp17.lina.proto.msgB\010G2GProto" +
      "b\006proto3"
    };
    com.google.protobuf.Descriptors.FileDescriptor.InternalDescriptorAssigner assigner =
        new com.google.protobuf.Descriptors.FileDescriptor.    InternalDescriptorAssigner() {
          public com.google.protobuf.ExtensionRegistry assignDescriptors(
              com.google.protobuf.Descriptors.FileDescriptor root) {
            descriptor = root;
            return null;
          }
        };
    com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.github.wp17.lina.proto.msg.Options.getDescriptor(),
        }, assigner);
    internal_static_G2GExpireCacheReq_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_G2GExpireCacheReq_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_G2GExpireCacheReq_descriptor,
        new java.lang.String[] { "CacheKey", });
    com.google.protobuf.ExtensionRegistry registry =
        com.google.protobuf.ExtensionRegistry.newInstance();
    registry.add(com.github.wp17.lina.proto.msg.Options.messageId);
    com.google.protobuf.Descriptors.FileDescriptor
        .internalUpdateFileDescriptor(descriptor, registry);
    com.github.wp17.lina.proto.msg.Options.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}