package com.example.eieiei;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.text.method.ScrollingMovementMethod;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class MainActivity extends ActionBarActivity {
	
	enum Mode {PLUS, MINUS, MUL, DIV, DEFAULT};
	Mode mode = Mode.DEFAULT;
	ScrollView scroller;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		if (savedInstanceState == null) {
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		
		scroller = (ScrollView) findViewById(R.id.scroller);
//		scroller.post(new Runnable() {
//			
//			@Override
//			public void run() {
//				scroller.scrollTo(0, 0);
//			}
//		
//		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
		
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
		
	}
	
	public void onClickOp(View v) {
		
		Button button = (Button) v;
		String buttonText = button.getText().toString();
		
		TextView resultArea = (TextView) findViewById(R.id.resultArea);
		String resultText = resultArea.getText().toString();
		String lines[] = resultText.split("\\r?\\n");
		
		double lastNum = 0;
		try {
			lastNum = Double.parseDouble(lines[lines.length-1].trim());
		} catch (Exception e) {
			return;
		}
		
		if(mode != Mode.DEFAULT) {
			double prevNum = 0;
			try {
				prevNum = Double.parseDouble(lines[lines.length-3].trim());
			} catch (Exception e) {
				return;
			}
			calculate(prevNum, lastNum);
			mode = Mode.DEFAULT;
		}
		
		if (buttonText.equals("+")) {
			mode = Mode.PLUS;
			resultArea.append("\n+\n");
		} else if (buttonText.equals("-")) {
			mode = Mode.MINUS;
			resultArea.append("\n-\n");
		} else if (buttonText.equals("*")) {
			mode = Mode.MUL;
			resultArea.append("\n*\n");
		} else if (buttonText.equals("/")) {
			mode = Mode.DIV;
			resultArea.append("\n/\n");
		}
		
//		TextView footer = (TextView) findViewById(R.id.footer);
//		footer.requestFocus();
//		scroller.fullScroll(View.FOCUS_DOWN);
		
	}
	
	public void calculate(double a, double b) {
		
		TextView resultArea = (TextView) findViewById(R.id.resultArea);
		double ans = 0;
		
		switch (mode) {
		case PLUS:	ans = a + b; break;
		case MINUS:	ans = a - b; break;
		case MUL:	ans = a * b; break;
		case DIV:	ans = a / b; break;
		}
		
		resultArea.append("\n=\n" + ans);
		
//		TextView footer = (TextView) findViewById(R.id.footer);
//		footer.requestFocus();
//		scroller.fullScroll(View.FOCUS_DOWN);
	}
	
	private static String removeLastChar(String str) {
        return str.substring(0,str.length()-1);
    }
	
	public void onClick(View v) {
		
		Button button = (Button) v;
		int buttonId = button.getId();
		String bText = button.getText().toString();
		
		TextView resultArea = (TextView) findViewById(R.id.resultArea);
		String resultText = resultArea.getText().toString();
		
		String lines[] = resultText.split("\\r?\\n");
		String lastLine = lines[lines.length-1].trim();
		
		switch (buttonId) {
		case R.id.buttonClear:
			resultArea.setText("0");
			mode = Mode.DEFAULT;
			return;
		case R.id.buttonDel:
			resultArea.setText(removeLastChar(resultText));
			return;
		default:
			if (lastLine.equals("0")) {
				resultArea.setText(removeLastChar(resultText));
			}
			resultArea.append(bText);
		}
		
//		TextView footer = (TextView) findViewById(R.id.footer);
//		footer.requestFocus();
//		scroller.fullScroll(View.FOCUS_DOWN);
		
	}
	

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}

}
