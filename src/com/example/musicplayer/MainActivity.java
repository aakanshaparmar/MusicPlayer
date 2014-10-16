package com.example.musicplayer;

import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

import java.util.List;

import android.media.AudioManager;
import android.media.MediaPlayer;

import android.os.Bundle;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Menu;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.view.View;


public class MainActivity extends Activity {
	
	String Past;
	MediaPlayer mediaPlayer;
	private SeekBar volumeSeekbar = null;
    private AudioManager audioManager = null; 
	
	public class songs implements Serializable
    {
    	int counter;
    	String songTitle; 
    	String songurl;
    	
    	public songs (int a, String b, String c)
    	{
    	    this.counter = a;
    	    this.songTitle = b;
    	    this.songurl = c;
    	}
    	
    	public void inc ()
    	{
    		this.counter++;
    		
    	}
    	public int returnCounter()
    	{
    		return counter;
       	}	
    	public String returnName ()
    	{
    		return songTitle;
    	}
    	public String returnUrl()
    	{
    		return songurl;
    	}
    	public String toString(){
            return this.songTitle  + this.counter;
        }
    	
    	
    }
     
	List<songs> data = new ArrayList<songs>();     
	Button play,stop,update,query,delete,service; 
	EditText linker, title ;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
	{
	    super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setVolumeControlStream(AudioManager.STREAM_MUSIC);
        initControls();
        
        displayData();
               
        linker = (EditText)findViewById(R.id.songUrl);
	     String Url[]={"http://www.songspk320z.us/Singles/[Songs.PK]%20Dard%20Dilo%20Ke%20(The%20Xpose)%20-%20128Kbps.mp3"};
        //String Url = linker.getText().toString();
      
	    mediaPlayer = new MediaPlayer();
    	mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
    	    	
        try {
    		mediaPlayer.setDataSource(Url[0]);
    	} catch (IllegalArgumentException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (SecurityException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IllegalStateException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	} catch (IOException e) {
    		// TODO Auto-generated catch block
    		e.printStackTrace();
    	}
		   
        play = (Button)findViewById(R.id.play);
        service = (Button)findViewById(R.id.service);
        update = (Button)findViewById(R.id.update);  
        query = (Button)findViewById(R.id.query);             
        stop = (Button)findViewById(R.id.stop);
        delete = (Button)findViewById(R.id.delete);
        
        play.setOnClickListener( new View.OnClickListener()
        {
        	public void onClick(View v)
        	{
        		
              Past="Play";
              if(mediaPlayer.isPlaying() && play.getText().equals("Pause"))
        	  {
        		  mediaPlayer.pause();
        		  play.setText("Play");
       		   }
            else  if(play.getText().equals("Play"))
        	 {
            	play.setText("Pause");               
                try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            
                Thread thread = new Thread()
                {
                      @Override
                      public void run() 
                      {
                       mediaPlayer.start();
                       }
                  };

                thread.start();                        
         
         	  }
        	}
        });
        
        service.setOnClickListener( new View.OnClickListener()
        {
        	
        	public void onClick(View v)
        	{
        		
        		Past="Service";
        		play.setText("Pause");               
                try {
					mediaPlayer.prepare();
				} catch (IllegalStateException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}            
                Thread t = new Thread()
                {
                      @Override
                      public void run() {
                       mediaPlayer.start();
                       }
                  };

                t.start();            
            
            }
       });
        
       
     /*  mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() 
        { 			 		
        	public void onCompletion(MediaPlayer mediaPlayer) 
           {
             play.setText("Play");
           }                   
        }); */      
        
        
     stop.setOnClickListener(new View.OnClickListener() 
     {
        	
			@Override
			public void onClick(View v)
			{
				mediaPlayer.stop();
				//mediaPlayer.reset();
				play.setText("Play");		
			}
		});
        
     update.setOnClickListener(new View.OnClickListener() {
     	
  			@Override
  			public void onClick(View v)
  			{
  				
  				linker = (EditText)findViewById(R.id.songUrl);
  				title= (EditText)findViewById(R.id.songName);
  				String a = title.getText().toString();
  				String b = linker.getText().toString();
  				int found=0;
                for (int i = 0; i < data.size(); i++) 
                { 
                	if (data.get(i).returnName().equals(a)) 
                	{ 
                		data.get(i).inc();
                		found++;
                  	}
                }
                
                if(found==0)
  			       { 
  				     songs playList = new songs(1,a,b);
  			         data.add(playList); 
  			
  		         	}
                displayData();
                
  			} });  
     
     query.setOnClickListener(new View.OnClickListener() {
      	
			@Override
			public void onClick(View v)
			{
				
				title= (EditText)findViewById(R.id.songName);
				linker=(EditText)findViewById(R.id.songUrl);
				String b = title.getText().toString();
				int found=0;
                for (int i = 0; i < data.size(); i++) 
                {  
             	  if (data.get(i).returnName().equals(b)) 
             	  {   
             		linker.setText(data.get(i).returnUrl());
             		found++;
             	}
             }
             if(found==0)
			       linker.setText("Not Found!");
          
			} });
     
     delete.setOnClickListener(new View.OnClickListener() {
       	
			@Override
			public void onClick(View v)
			{
				
				title= (EditText)findViewById(R.id.songName);
				linker=(EditText)findViewById(R.id.songUrl);
				String b = title.getText().toString();
				String c = linker.getText().toString();

				int found=0;
             for (int i = 0; i < data.size(); i++) 
             {  
          	  if (data.get(i).returnName().equals(b) || data.get(i).returnUrl().equals(c)) 
          	  {   
          		data.remove(i);
          		found++;
          	}
          }
          if(found==0)
			       linker.setText("Not Present in List!");
          
              
          displayData(); } 
	 
	 });
    }
    
    private void initControls()
    {
        try
        {
            volumeSeekbar = (SeekBar)findViewById(R.id.seekBar1);
            audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
            volumeSeekbar.setMax(audioManager
                    .getStreamMaxVolume(AudioManager.STREAM_MUSIC));
            volumeSeekbar.setProgress(audioManager
                    .getStreamVolume(AudioManager.STREAM_MUSIC));   


            volumeSeekbar.setOnSeekBarChangeListener(new OnSeekBarChangeListener() 
            {
                @Override
                public void onStopTrackingTouch(SeekBar arg0) 
                {
                }

                @Override
                public void onStartTrackingTouch(SeekBar arg0) 
                {
                }

                @Override
                public void onProgressChanged(SeekBar arg0, int progress, boolean arg2) 
                {
                    audioManager.setStreamVolume(AudioManager.STREAM_MUSIC,
                            progress, 0);
                }
            });
        }
        catch (Exception e) 
        {
            e.printStackTrace();
        }
    }
    
    private void displayData()
    { 
    	ArrayAdapter adapter = new ArrayAdapter<songs>(this,android.R.layout.simple_list_item_1, data);
    	ListView listview = (ListView) findViewById(R.id.listView1);
    	listview.setAdapter(adapter);
    }
    
    /*public void onPause()
    {
    	if (mediaPlayer.isPlaying())
    	  if (Past.equals("Play"))
    	  { mediaPlayer.stop(); mediaPlayer.reset();}
    }  */
    
 
   @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
    
  
}