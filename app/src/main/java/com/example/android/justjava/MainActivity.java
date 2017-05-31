/**
 * Add your package below. Package name can be found in the project's AndroidManifest.xml file.
 * This is the package name our example uses:
 *
 * package com.example.android.justjava;
 */
package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;

import static android.R.attr.name;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {
    int quantity = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {
        // Figure out if the wants whipped creamed topping
        EditText text = (EditText) findViewById(R.id.name_field);
        String value = text.getText().toString();
        // Figure out if the wants whipped creamed topping
        CheckBox WhippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = WhippedCreamCheckBox.isChecked();
        // Figure out if the wants whipped chocolate topping
        CheckBox ChocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = ChocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String priceMessage = createOrderSummary(value,price, hasWhippedCream,hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just Java Order for: " + value);
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        if (intent.resolveActivity(getPackageManager()) !=null);{
            startActivity(intent);
        }
    }

    /**
     * This method is called when calculate the price of the order.
     *@return total price
     */

    public int calculatePrice (boolean addWhippedCream, boolean addChocolate ) {
        //price of one coffee
        int basePrice = 5;
        //Add $1 if user want whipped cream
        if (addWhippedCream){
            basePrice = basePrice + 1;
        }
        //Add $2 if user want chocolate
        if (addChocolate){
            basePrice = basePrice + 2;
        }
        return quantity *basePrice;
    }

    /**
     * This method is to create summary of the order.
     *
     * @param price of the order.
     * @return  text summary
     */
    private String createOrderSummary (String value, int price, boolean addWhippedCream, boolean addChocolate){
        String priceMessage = getString(R.string.order_summary_name,name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream,addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate,addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity,quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price,NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
    /**
     * This method is called when the plus button is clicked.
     */
    public void increment (View view) {
        if (quantity == 100){
            //show error message as a toast
            Toast.makeText(this,"You can't have more than 100 coffee", Toast.LENGTH_SHORT).show();
            //exit this code early because their nothing left to do
            return;
        }
        quantity = quantity + 1;
        displayQuantity(quantity);
    }

    /**
     * This method is called when the minus button is clicked.
     */
    public void decrement (View view) {
        if (quantity == 1){
            //show error message as a toast
            Toast.makeText(this,"You can't have less than 1 coffee", Toast.LENGTH_SHORT).show();
            //exit this code early because their nothing left to do
            return;
        }
        quantity = quantity - 1;
        displayQuantity(quantity);
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }

}
