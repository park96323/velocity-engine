package org.apache.velocity.context;

/*
 * The Apache Software License, Version 1.1
 *
 * Copyright (c) 2001 The Apache Software Foundation.  All rights
 * reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 * 1. Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the
 *    distribution.
 *
 * 3. The end-user documentation included with the redistribution, if
 *    any, must include the following acknowlegement:
 *       "This product includes software developed by the
 *        Apache Software Foundation (http://www.apache.org/)."
 *    Alternately, this acknowlegement may appear in the software itself,
 *    if and wherever such third-party acknowlegements normally appear.
 *
 * 4. The names "The Jakarta Project", "Velocity", and "Apache Software
 *    Foundation" must not be used to endorse or promote products derived
 *    from this software without prior written permission. For written
 *    permission, please contact apache@apache.org.
 *
 * 5. Products derived from this software may not be called "Apache"
 *    nor may "Apache" appear in their names without prior written
 *    permission of the Apache Group.
 *
 * THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
 * WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
 * OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
 * ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
 * USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
 * OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
 * SUCH DAMAGE.
 * ====================================================================
 *
 * This software consists of voluntary contributions made by many
 * individuals on behalf of the Apache Software Foundation.  For more
 * information on the Apache Software Foundation, please see
 * <http://www.apache.org/>.
 */

import org.apache.velocity.context.InternalHousekeepingContext;
import org.apache.velocity.context.Context;

import java.util.ArrayList;

/**
 *  'Package' of event handlers...
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @author <a href="mailto:j_a_fernandez@yahoo.com">Jose Alberto Fernandez</a>
 * @version $Id: EventCartridge.java,v 1.3 2001/04/11 12:59:42 geirm Exp $
 */
public class EventCartridge implements ReferenceInsertionEventHandler,
					NullSetEventHandler,
					NullReferenceEventHandler
{
    private ReferenceInsertionEventHandler rieh = null;
    private NullSetEventHandler nseh = null;
    private NullReferenceEventHandler nreh = null;

    /**
     *  Adds an event handler(s) to the Cartridge.  This method
     *  will find all possible event handler interfaces supported
     *  by the passed in object.
     *
     *  @param ev object impementing a valid EventHandler-derived interface
     *  @return true if a supported interface, false otherwise or if null
     */
    public boolean addEventHandler( EventHandler ev )
    {
        if (ev == null)
        {
            return false;
        }

	boolean found = false;

        if ( ev instanceof ReferenceInsertionEventHandler)
        {
            rieh = (ReferenceInsertionEventHandler) ev;
	    found = true;
        }
        
        if ( ev instanceof NullReferenceEventHandler )
        {
            nreh = (NullReferenceEventHandler) ev;
	    found = true;
        }

        if ( ev instanceof NullSetEventHandler )
        {
            nseh = (NullSetEventHandler) ev;
            found = true;
        }
      
        return found;
    }
    
    /**
     *  Removes an event handler(s) from the Cartridge.  This method
     *  will find all possible event handler interfaces supported
     *  by the passed in object and remove them.
     *
     *  @param ev object impementing a valid EventHandler-derived interface
     *  @return true if a supported interface, false otherwise or if null
     */
    public boolean removeEventHandler(EventHandler ev)
    {
	if ( ev == null )
	{
	    return false;
	}

	boolean found = false;

	if (ev == rieh) 
	{
	    rieh = null;
	    found = true;
	}
	
	if (ev == nseh) 
	{
	    nseh = null;
	    found = true;
	}

	if (ev == nreh) 
	{
	    nreh = null;
	    found = true;
	}

	return found;
    }

    /**
     *  Implementation of ReferenceInsertionEventHandler method
     *  <code>referenceInsert()</code>.
     *
     *  Called during Velocity merge before a reference value will
     *  be inserted into the output stream.
     *
     *  @param reference reference from template about to be inserted
     *  @param value  value about to be inserted (after toString() )
     *  @return Object on which toString() should be called for output.
     */
    public Object referenceInsert( String reference, Object value  )
    {
	if (rieh == null)
	{
	    return value;
	}

	return rieh.referenceInsert( reference, value );
    }


    public boolean nullSetLogMessage( String reference )
    {
	if ( nseh == null)
	{
	    return true;
	}

	return nseh.nullSetLogMessage( reference );
    }
    
    public String nullReferenceRender( String reference )
    {
	if (nreh == null)
	{
	    return reference;
	}

	return nreh.nullReferenceRender( reference );
    }

    
    public boolean attachToContext( Context context )
    {
        
        if (  context instanceof InternalHousekeepingContext )
        {         
            InternalHousekeepingContext icb = (InternalHousekeepingContext) context;

            icb.attachEventCartridge( this );

            return true;
        }
        else
        {
            return false;
        }
    }
}
