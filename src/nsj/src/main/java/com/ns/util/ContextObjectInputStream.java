package com.ns.util;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectStreamClass;

public class ContextObjectInputStream extends ObjectInputStream { 

    ClassLoader mLoader;
    
    public ContextObjectInputStream( InputStream in, ClassLoader loader ) throws IOException, SecurityException {
        super( in );
        mLoader = loader;
    }
    
    protected Class resolveClass( ObjectStreamClass v ) throws IOException, ClassNotFoundException {
        if ( mLoader == null )
            return super.resolveClass( v );
		else
            return mLoader.loadClass( v.getName() );
    }
}
