package com.example.myproject;

import android.app.Activity;
import android.os.Bundle;
import android.view.*;
import android.widget.TextView;
import android.widget.ListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AdapterView.OnItemClickListener;
import android.util.Log;
import java.net.URL;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import android.content.Intent;
import com.example.myproject.ShowDescription;

public class RSSReader extends Activity implements OnItemClickListener {
	
	public static final String Source = "com.example.myproject.Srouce";
	public String RSSFEEDOFCHOICE;
	public String SOURCE;
	public final int DEFAULTSOURCE = 0;
	public final String tag = "RSSReader";
	private RSSFeed feed = null;

	public void onCreate(Bundle icicle) {
		super.onCreate(icicle);
		setContentView(R.layout.main);
		
		// read user choice on source
		int sourceid = getIntent().getIntExtra(RSSFEEDOFCHOICE, DEFAULTSOURCE);		
		SOURCE = getSource(sourceid);		
		this.RSSFEEDOFCHOICE = SOURCE;
		
		// go get our feed!
		feed = getFeed(RSSFEEDOFCHOICE);

		// display UI
		UpdateDisplay();

	}
	
	   /** Given a difficulty level, come up with a new puzzle */
	private String getSource(int sourceid) {
		String source;
		switch (sourceid) {
		case 0:
			source = "http://www.engadget.com/rss.xml";
			break;
		case 1:
			source = "http://allthingsd.com/feed";
			break;
		case 2:
			source = "http://cnbeta.feedsportal.com/c/34306/f/624776/index.rss";
			break;
		default:
			source = "http://www.engadget.com/rss.xml";
			break;
		}
		return source;
	}

	private RSSFeed getFeed(String urlToRssFeed) {
		try {
			// setup the url
			URL url = new URL(urlToRssFeed);

			// create the factory
			SAXParserFactory factory = SAXParserFactory.newInstance();
			// create a parser
			SAXParser parser = factory.newSAXParser();

			// create the reader (scanner)
			XMLReader xmlreader = parser.getXMLReader();
			// instantiate our handler
			RSSHandler theRssHandler = new RSSHandler();
			// assign our handler
			xmlreader.setContentHandler(theRssHandler);
			// get our data via the url class
			InputSource is = new InputSource(url.openStream());
			// perform the synchronous parse
			xmlreader.parse(is);
			// get the results - should be a fully populated RSSFeed instance,
			// or null on error
			return theRssHandler.getFeed();
		} catch (Exception ee) {
			// if we have a problem, simply return null
			return null;
		}
	}

	private void UpdateDisplay() {
		TextView feedtitle = (TextView) findViewById(R.id.feedtitle);
		TextView feedpubdate = (TextView) findViewById(R.id.feedpubdate);
		ListView itemlist = (ListView) findViewById(R.id.itemlist);

		if (feed == null) {
			feedtitle.setText("No RSS Feed Available");
			return;
		}

		feedtitle.setText(feed.getTitle());
		feedpubdate.setText(feed.getPubDate());

		ArrayAdapter<RSSItem> adapter = new ArrayAdapter<RSSItem>(this,
				android.R.layout.simple_list_item_1, feed.getAllItems());

		itemlist.setAdapter(adapter);

		itemlist.setOnItemClickListener(this);

		itemlist.setSelection(0);

	}

	public void onItemClick(AdapterView parent, View v, int position, long id) {
		Log.i(tag, "item clicked! [" + feed.getItem(position).getTitle() + "]");

		Intent itemintent = new Intent(this, ShowDescription.class);

		Bundle b = new Bundle();
		b.putString("title", feed.getItem(position).getTitle());
		b.putString("description", feed.getItem(position).getDescription());
		b.putString("link", feed.getItem(position).getLink());
		b.putString("pubdate", feed.getItem(position).getPubDate());

		itemintent.putExtra("android.intent.extra.INTENT", b);

		startSubActivity(itemintent, 0);
	}
	
	private void startSubActivity(Intent itemintent, int i) {
	    // TODO Auto-generated method stub
	}

}
