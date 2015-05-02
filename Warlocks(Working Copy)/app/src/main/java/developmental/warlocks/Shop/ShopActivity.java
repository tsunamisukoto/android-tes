package developmental.warlocks.Shop;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.developmental.warlocks.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.ArrayList;

import Spells.LoadOutInfo;
import Spells.SpellType;
import developmental.warlocks.Global;

/**
 * Created by Scott on 18/08/13.
 */
public class ShopActivity extends Activity {
    public static LoadOutInfo[] e;
    public static int SelectedIndex = 0;
    int storedposition = -1;

    public ShopActivity() {
        super();

    }

    public static void loadState() {
        LoadOutInfo[] s = null;
        FileInputStream inStream = null;
        try {
            File f = new File(Environment.getExternalStorageDirectory(), "/data.dat");
            inStream = new FileInputStream(f);
            ObjectInputStream objectInStream = new ObjectInputStream(inStream);

            s = ((LoadOutInfo[]) objectInStream.readObject());
            objectInStream.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        } catch (OptionalDataException e1) {
            e1.printStackTrace();
        } catch (StreamCorruptedException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        Log.e("LOADING LOADOUT", s.length + "");
        if (s != null)
            for (int i = 0; i < s.length && i < 9; i++) {
                Global.spellList[i] = s[i];
                Log.e("LOADING LOADOUT", i + " " + s[i].spellType + "");

            }
        else {
            Log.e("FAILED TO LOAD", "YOU SUCK!!!!");
        }
        for (LoadOutInfo l : Global.spellList) {
            Log.e("LOADING LOADOUT", l.spellType + "");
        }
    }

    public static byte[] SerializetoBytes(LoadOutInfo[] gameObjects) {

        try {

            ByteArrayOutputStream fileOut =
                    new ByteArrayOutputStream();
            ObjectOutputStream out =
                    new ObjectOutputStream(fileOut);

            out.writeObject(gameObjects);
            return fileOut.toByteArray();

        } catch (IOException i) {
            i.printStackTrace();
        }
        return null;
    }

    void Spells() {

        Global.spellList = new LoadOutInfo[9];
        Global.spellList[0] = new LoadOutInfo(SpellType.Fireball, 1);
        Global.spellList[1] = new LoadOutInfo(SpellType.Lightning, 1);
        Global.spellList[2] = new LoadOutInfo(SpellType.FireSpray, 1);
        Global.spellList[3] = new LoadOutInfo(SpellType.Meteor, 1);
        Global.spellList[4] = new LoadOutInfo(SpellType.Gravity, 1);
        Global.spellList[5] = new LoadOutInfo(SpellType.Bounce, 1);
        Global.spellList[6] = new LoadOutInfo(SpellType.Illusion, 1);
        Global.spellList[7] = new LoadOutInfo(SpellType.Illusion, 1);
        Global.spellList[8] = new LoadOutInfo(SpellType.Illusion, 1);


        loadState();

    }

    boolean IsValidForSlot(LoadOutInfo check, LoadOutInfo[] checkList) {
        for (LoadOutInfo l : checkList) {
            if (l.spellType == check.spellType) {
                return true;
            }
        }
        return false;
    }
    LoadOutInfo[] Slot1() {
        LoadOutInfo[] s = new LoadOutInfo[1];
        s[0] = new LoadOutInfo(SpellType.Fireball, 1);
        ShopActivity.SelectedIndex = 0;
        return s;
    }

    LoadOutInfo[] Slot2() {
        LoadOutInfo[] s = new LoadOutInfo[7];
        s[0] = new LoadOutInfo(SpellType.Lightning, 1);
        s[1] = new LoadOutInfo(SpellType.Homing, 1);
        s[2] = new LoadOutInfo(SpellType.Illusion, 1);
        s[3] = new LoadOutInfo(SpellType.Grenade, 1);
        s[4] = new LoadOutInfo(SpellType.Piercing, 1);
        s[5] = new LoadOutInfo(SpellType.Powerball, 1);
        s[6] = new LoadOutInfo(SpellType.Boomerang, 1);
        ShopActivity.SelectedIndex = 1;
        return s;
    }

    LoadOutInfo[] Slot3() {
        LoadOutInfo[] s = new LoadOutInfo[6];
        s[0] = new LoadOutInfo(SpellType.TrapMines, 1);
        s[1] = new LoadOutInfo(SpellType.IllusionBall, 1);
        s[2] = new LoadOutInfo(SpellType.Link, 1);
        s[3] = new LoadOutInfo(SpellType.Ice, 1);
        s[4] = new LoadOutInfo(SpellType.Gravity, 1);
        s[5] = new LoadOutInfo(SpellType.Meteor, 1);
        ShopActivity.SelectedIndex = 2;
        return s;
    }

    LoadOutInfo[] Slot4() {
        LoadOutInfo[] s = new LoadOutInfo[7];
        s[0] = new LoadOutInfo(SpellType.Absorb, 1);
        s[1] = new LoadOutInfo(SpellType.SonicWave, 1);
        s[2] = new LoadOutInfo(SpellType.FireSpray, 1);
        s[3] = new LoadOutInfo(SpellType.IceSpray, 1);
        s[4] = new LoadOutInfo(SpellType.Bounce, 1);
        s[5] = new LoadOutInfo(SpellType.Drain, 1);
        s[6] = new LoadOutInfo(SpellType.Splitter, 1);
        ShopActivity.SelectedIndex = 3;
        return s;
    }

    LoadOutInfo[] Slot5() {
        LoadOutInfo[] s = new LoadOutInfo[7];
        s[0] = new LoadOutInfo(SpellType.Teleport, 1);
        s[1] = new LoadOutInfo(SpellType.Swap, 1);
        s[2] = new LoadOutInfo(SpellType.Thrust, 1);
        s[3] = new LoadOutInfo(SpellType.JuggerNaught, 1);
        s[4] = new LoadOutInfo(SpellType.WindWalk, 1);
        s[5] = new LoadOutInfo(SpellType.Phase, 1);
        s[6] = new LoadOutInfo(SpellType.MiddleOfAction, 1);
        ShopActivity.SelectedIndex = 4;
        return s;
    }

    LoadOutInfo[] Slot6() {
        LoadOutInfo[] s = new LoadOutInfo[7];
        s[0] = new LoadOutInfo(SpellType.FireExplode, 1);
        s[1] = new LoadOutInfo(SpellType.IceExplode, 1);
        s[2] = new LoadOutInfo(SpellType.MagnetExplode, 1);
        s[3] = new LoadOutInfo(SpellType.DrainExplode, 1);
        s[4] = new LoadOutInfo(SpellType.Reflect, 1);
        s[5] = new LoadOutInfo(SpellType.Orbitals, 1);
        s[6] = new LoadOutInfo(SpellType.Root, 1);
        ShopActivity.SelectedIndex = 5;
        return s;
    }

    LoadOutInfo[] Slot7() {
        LoadOutInfo[] s = new LoadOutInfo[7];
        s[0] = new LoadOutInfo(SpellType.BurnAura, 1);
        s[1] = new LoadOutInfo(SpellType.HealAura, 1);
        s[2] = new LoadOutInfo(SpellType.Bezerk, 1);
        s[3] = new LoadOutInfo(SpellType.Fervour, 1);
        s[4] = new LoadOutInfo(SpellType.Boots, 1);
        s[5] = new LoadOutInfo(SpellType.HealthStone, 1);
        s[6] = new LoadOutInfo(SpellType.Shield, 1);
        ShopActivity.SelectedIndex = 6;
        return s;
    }

    LoadOutInfo[] Slot8(){
        LoadOutInfo[] ss = Slot7();
        ShopActivity.SelectedIndex=7;
        return ss;
    }

    LoadOutInfo[] Slot9(){
        LoadOutInfo[] ss = Slot7();
        ShopActivity.SelectedIndex=8;
        return ss;
    }

    void setstuff(LoadOutInfo[] se, int index) {
        ArrayList<LoadOutInfo> v = new ArrayList<LoadOutInfo>();
        SpellsAdapter a = new SpellsAdapter(this, v);
        ShopActivity.e = se;
        for (LoadOutInfo g : se)
            v.add(g);
        final ListView q = ((ListView) findViewById(R.id.listView3));

        q.setAdapter(a);
        this.storedposition = -1;
        int p = 0;
        for (LoadOutInfo l : se) {

            if (l.spellType == Global.spellList[index - 1].spellType) {
                storedposition = p;
                setfsadgasdg(p);
                q.setSelection(p);
            }
            p += 1;
        }

    }

    void setfsadgasdg(int position) {
        TextView t = ((TextView) findViewById(R.id.spellContent));
        int i = LoadOutInfo.setDescription(ShopActivity.e[position].spellType);
        findViewById(R.id.spellIcon).setBackgroundResource(LoadOutInfo.setResource(ShopActivity.e[position].spellType));
        t.setText(i);
        TextView t2 = ((TextView) findViewById(R.id.Title));
        t2.setText(ShopActivity.e[position].spellType.toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_shop);
        Spells();

        ArrayList<LoadOutInfo> v = new ArrayList<LoadOutInfo>();

        SpellsAdapter a = new SpellsAdapter(this, v);
        setstuff(Slot1(), 1);
        for (LoadOutInfo g : e)
            v.add(g);
        final ListView q = ((ListView) findViewById(R.id.listView3));
        q.setAdapter(a);
        q.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                storedposition = position;
                setfsadgasdg(position);

            }
        });
        for (int i = 0; i < 9; i++) {
            SelectedIndex = i;
            changeIcon(i);
        }
        SelectedIndex = 0;
        View.OnClickListener j = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.spellslot1).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot2).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot3).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot4).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot5).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot6).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot7).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot8).setBackgroundResource(R.drawable.shop_button);
                findViewById(R.id.spellslot9).setBackgroundResource(R.drawable.shop_button);
                v.setBackgroundResource(R.drawable.shop_button_selected);
                switch (v.getId()) {
                    case R.id.spellslot1:
                        setstuff(Slot1(), 1);
                        break;
                    case R.id.spellslot2:

                        setstuff(Slot2(), 2);
                        break;
                    case R.id.spellslot3:
                        setstuff(Slot3(), 3);
                        break;
                    case R.id.spellslot4:
                        setstuff(Slot4(), 4);
                        break;
                    case R.id.spellslot5:
                        setstuff(Slot5(), 5);
                        break;
                    case R.id.spellslot6:
                        setstuff(Slot6(), 6);
                        break;
                    case R.id.spellslot7:
                        setstuff(Slot7(), 7);
                        break;
                    case R.id.spellslot8:
                        setstuff(Slot8(), 8);
                        break;
                    case R.id.spellslot9:
                        setstuff(Slot9(), 9);
                        break;
                }
            }
        };
        findViewById(R.id.spellslot1).setOnClickListener(j);
        findViewById(R.id.spellslot2).setOnClickListener(j);
        findViewById(R.id.spellslot3).setOnClickListener(j);
        findViewById(R.id.spellslot4).setOnClickListener(j);
        findViewById(R.id.spellslot5).setOnClickListener(j);
        findViewById(R.id.spellslot6).setOnClickListener(j);
        findViewById(R.id.spellslot7).setOnClickListener(j);
        findViewById(R.id.spellslot8).setOnClickListener(j);
        findViewById(R.id.spellslot9).setOnClickListener(j);
        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (storedposition != -1) {
                    boolean change = (Global.spellList[SelectedIndex].spellType == ShopActivity.e[storedposition].spellType);

                    if (!change) {
                        final boolean[] s = new boolean[1];
                        s[0] = false;

                        new AlertDialog.Builder(ShopActivity.this)
                                .setTitle("Changing Spell")
                                .setMessage("Do you really want to change This spell??")
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int whichButton) {
                                        Global.spellList[SelectedIndex].SetOrIncrement(ShopActivity.e[storedposition].spellType);

                                        changeIcon(SelectedIndex);
                                        Log.e("SHOP", "SAHGFHGDSH");
                                    }
                                })
                                .setNegativeButton(android.R.string.no, null).show();

                    } else {
                        int count = 0;
                        for (LoadOutInfo l : Global.spellList) {
                            count += l.Rank;
                        }
                        if (count + 1 > Global.MaxNumberOfSpellRanks) {
                            Toast.makeText(getApplicationContext(), "Max number of spell ranks reached", Toast.LENGTH_LONG);

                        } else
                        Global.spellList[SelectedIndex].SetOrIncrement(ShopActivity.e[storedposition].spellType);
                    }

                    changeIcon(SelectedIndex);
                }
            }
        });
        findViewById(R.id.button8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        findViewById(R.id.confirmbutton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SaveLoadout();
                //  Intent intent = new Intent(ShopActivity.this,OpenGLTestActivity.class);
                //  finish();
            }
        });
    }

    private void saveState() {
        FileOutputStream outStream;
        try {
            File f = new File(Environment.getExternalStorageDirectory(), "/data.dat");
            outStream = new FileOutputStream(f);
            ObjectOutputStream objectOutStream = new ObjectOutputStream(outStream);


            objectOutStream.writeObject(Global.spellList);
            objectOutStream.close();
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        } catch (IOException e1) {
            e1.printStackTrace();
        }

    }

    private void SaveLoadout() {
        boolean cansave = true;
        int count = 0;
        int id = 0;
        for(LoadOutInfo l:Global.spellList)
        {
            count += l.Rank;
            switch (id) {
                case 0:
                    if (!IsValidForSlot(l, Slot1()))
                        cansave = false;
                    break;
                case 1:
                    if (!IsValidForSlot(l, Slot2()))
                        cansave = false;
                    break;
                case 2:
                    if (!IsValidForSlot(l, Slot3()))
                        cansave = false;
                    break;
                case 3:
                    if (!IsValidForSlot(l, Slot4()))
                        cansave = false;
                    break;
                case 4:
                    if (!IsValidForSlot(l, Slot5()))
                        cansave = false;
                    break;
                case 5:
                    if (!IsValidForSlot(l, Slot6()))
                        cansave = false;
                    break;
                case 6:
                    if (!IsValidForSlot(l, Slot7()))
                        cansave = false;
                    break;
                case 7:
                    if (!IsValidForSlot(l, Slot8()))
                        cansave = false;

                    break;
                case 8:
                    if (!IsValidForSlot(l, Slot9()))
                        cansave = false;
                    break;

            }

            Log.e(" SAVING LOADOUT",l.spellType+"");
            id += 1;
        }
        if (!cansave) {
            Toast.makeText(getApplicationContext(), "Invalid Loadout. Check spells in each slot.", Toast.LENGTH_LONG).show();
            return;
        }
        if (count > Global.MaxNumberOfSpellRanks) {
            Toast.makeText(getApplicationContext(), "Invalid Loadout. Max Ranks:" + Global.MaxNumberOfSpellRanks + " Ranks:" + count, Toast.LENGTH_LONG).show();
            cansave = false;

        }
        if (cansave) {
            saveState();
            finish();
        }
    }

    void changeIcon(int i) {
        ImageView b = null;
        TextView t = null;
        switch (i) {
            case 0:
                t = (TextView) findViewById(R.id.textView1);
                b = (ImageView) findViewById(R.id.imageView1);
                break;
            case 1:
                t = (TextView) findViewById(R.id.textView2);
                b = (ImageView) findViewById(R.id.imageView2);
                break;
            case 2:
                t = (TextView) findViewById(R.id.textView3);
                b = (ImageView) findViewById(R.id.imageView3);
                break;
            case 3:
                t = (TextView) findViewById(R.id.textView4);
                b = (ImageView) findViewById(R.id.imageView4);
                break;
            case 4:
                t = (TextView) findViewById(R.id.textView5);
                b = (ImageView) findViewById(R.id.imageView5);
                break;
            case 5:
                t = (TextView) findViewById(R.id.textView6);
                b = (ImageView) findViewById(R.id.imageView6);
                break;
            case 6:
                t = (TextView) findViewById(R.id.textView7);
                b = (ImageView) findViewById(R.id.imageView7);
                break;
            case 7:
                t = (TextView) findViewById(R.id.textView9);
                b = (ImageView) findViewById(R.id.imageView);
                break;
            case 8:
                t = (TextView) findViewById(R.id.textView10);
                b = (ImageView) findViewById(R.id.imageView8);
                break;

        }


        b.setBackgroundResource(LoadOutInfo.setResource(Global.spellList[SelectedIndex].spellType));
        t.setText("" + Global.spellList[SelectedIndex].Rank);

    }

}
