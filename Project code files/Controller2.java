package OETPN.Project_2_Intersections;

import Components.*;
import DataObjects.DataInteger;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Controller2 {
        public static void main (String []args) {
            PetriNet pn = new PetriNet();
            pn.PetriNetName = "Controller 2";
            pn.SetName("Controller 2");
            pn.NetworkPort = 1084;

            DataString in5 = new DataString();
            //ini.Printable = false;
            in5.SetName("in5");
            pn.PlaceList.add(in5);

            DataString in7 = new DataString();
            //ini.Printable = false;
            in7.SetName("in7");
            pn.PlaceList.add(in7);

            DataString in8 = new DataString();
            //ini.Printable = false;
            in8.SetName("in8");
            pn.PlaceList.add(in8);

            DataInteger Five = new DataInteger();
            Five.SetName("Five");
            Five.SetValue(5);
            pn.ConstantPlaceList.add(Five);

            DataInteger Ten = new DataInteger();
            Ten.SetName("Ten");
            Ten.SetValue(10);
            pn.ConstantPlaceList.add(Ten);

            DataString ini = new DataString();
            //ini.Printable = false;
            ini.SetName("ini");
            ini.SetValue("red");
            pn.ConstantPlaceList.add(ini);

            DataString red = new DataString();
            //red.Printable = false;
            red.SetName("red");
            red.SetValue("red");
            pn.ConstantPlaceList.add(red);

            DataString green = new DataString();
            //green.Printable = false;
            green.SetName("green");
            green.SetValue("green");
            pn.ConstantPlaceList.add(green);

            DataString yellow = new DataString();
            //yellow.Printable = false;
            yellow.SetName("yellow");
            yellow.SetValue("yellow");
            pn.ConstantPlaceList.add(yellow);

            DataString p1 = new DataString();
            p1.SetName("r5r7r8");
            p1.SetValue("signal");
            pn.PlaceList.add(p1);

            DataString p2 = new DataString();
            p2.SetName("g5r7r8");
            pn.PlaceList.add(p2);

            DataString p3 = new DataString();
            p3.SetName("y5r7r8");
            pn.PlaceList.add(p3);

            DataString p4 = new DataString();
            p4.SetName("r5g7r8");
            pn.PlaceList.add(p4);

            DataString p5 = new DataString();
            p5.SetName("r5y7r8");
            pn.PlaceList.add(p5);

            DataString p6 = new DataString();
            p6.SetName("r5r7g8");
            pn.PlaceList.add(p6);

            DataString p7 = new DataString();
            p7.SetName("r5r7y8");
            pn.PlaceList.add(p7);


            DataTransfer p10 = new DataTransfer();
            p10.SetName("OP5");
            p10.Value = new TransferOperation("localhost", "1081" , "P_TL5");
            pn.PlaceList.add(p10);

            DataTransfer p11 = new DataTransfer();
            p11.SetName("OP7");
            p11.Value = new TransferOperation("localhost", "1081" , "P_TL7");
            pn.PlaceList.add(p11);

            DataTransfer p12 = new DataTransfer();
            p12.SetName("OP8");
            p12.Value = new TransferOperation("localhost", "1081" , "P_TL8");
            pn.PlaceList.add(p12);


            //----------------------------iniT------------------------------------
            PetriTransition iniT = new PetriTransition(pn);
            iniT.TransitionName = "iniT";

            Condition iniTCt1 = new Condition(iniT, "ini", TransitionCondition.NotNull);

            GuardMapping grdiniT = new GuardMapping();
            grdiniT.condition= iniTCt1;

            grdiniT.Activations.add(new Activation(iniT, "ini", TransitionOperation.SendOverNetwork, "OP5"));
            grdiniT.Activations.add(new Activation(iniT, "ini", TransitionOperation.SendOverNetwork, "OP7"));
            grdiniT.Activations.add(new Activation(iniT, "ini", TransitionOperation.SendOverNetwork, "OP8"));
            grdiniT.Activations.add(new Activation(iniT, "", TransitionOperation.MakeNull, "ini"));

            iniT.GuardMappingList.add(grdiniT);

            iniT.Delay = 0;
            pn.Transitions.add(iniT);



            //----------------------------T1------------------------------------
            PetriTransition t1 = new PetriTransition(pn);
            t1.TransitionName = "T1";
            t1.InputPlaceName.add("r5r7r8");


            Condition T1Ct1 = new Condition(t1, "r5r7r8", TransitionCondition.NotNull);

            GuardMapping grdT1 = new GuardMapping();
            grdT1.condition= T1Ct1;
            grdT1.Activations.add(new Activation(t1, "r5r7r8", TransitionOperation.Move, "g5r7r8"));
            grdT1.Activations.add(new Activation(t1, "green", TransitionOperation.SendOverNetwork, "OP5"));
            t1.GuardMappingList.add(grdT1);

            t1.Delay = 5;
            pn.Transitions.add(t1);

            //----------------------------T2------------------------------------
            PetriTransition t2 = new PetriTransition(pn);
            t2.TransitionName = "T2";
            t2.InputPlaceName.add("g5r7r8");
            t2.InputPlaceName.add("in5");


            Condition T2Ct1 = new Condition(t2, "g5r7r8", TransitionCondition.NotNull);
            Condition T2Ct2 = new Condition(t2, "in5", TransitionCondition.IsNull);

            T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

            GuardMapping grdT2 = new GuardMapping();
            grdT2.condition= T2Ct1;
            grdT2.Activations.add(new Activation(t2, "g5r7r8", TransitionOperation.Move, "y5r7r8"));
            grdT2.Activations.add(new Activation(t2, "yellow", TransitionOperation.SendOverNetwork, "OP5"));
            grdT2.Activations.add(new Activation(t2, "Five", TransitionOperation.DynamicDelay,""));
            t2.GuardMappingList.add(grdT2);



            Condition T2Ct3 = new Condition(t2, "g5r7r8", TransitionCondition.NotNull);
            Condition T2Ct4 = new Condition(t2, "in5", TransitionCondition.NotNull);

            T2Ct3.SetNextCondition(LogicConnector.AND, T2Ct4);

            GuardMapping grdT22 = new GuardMapping();
            grdT22.condition= T2Ct3;
            grdT22.Activations.add(new Activation(t2, "g5r7r8", TransitionOperation.Move, "y5r7r8"));
            grdT22.Activations.add(new Activation(t2, "yellow", TransitionOperation.SendOverNetwork, "OP5"));
            grdT1.Activations.add(new Activation(t2, "Ten", TransitionOperation.DynamicDelay,""));
            t2.GuardMappingList.add(grdT22);




            t2.Delay = 5;
            pn.Transitions.add(t2);


            //----------------------------T3------------------------------------
            PetriTransition t3 = new PetriTransition(pn);
            t3.TransitionName = "T3";
            t3.InputPlaceName.add("y5r7r8");



            Condition T3Ct1 = new Condition(t2, "y5r7r8", TransitionCondition.NotNull);

            GuardMapping grdT3 = new GuardMapping();
            grdT3.condition= T3Ct1;
            grdT3.Activations.add(new Activation(t3, "y5r7r8", TransitionOperation.Move, "r5g7r8"));
            grdT3.Activations.add(new Activation(t3, "red", TransitionOperation.SendOverNetwork, "OP5"));
            grdT3.Activations.add(new Activation(t3, "green", TransitionOperation.SendOverNetwork, "OP7"));

            t3.GuardMappingList.add(grdT3);

            t3.Delay = 5;
            pn.Transitions.add(t3);


            //----------------------------T4------------------------------------
            PetriTransition t4 = new PetriTransition(pn);
            t4.TransitionName = "T4";
            t4.InputPlaceName.add("r5g7r8");
            t4.InputPlaceName.add("in7");


            Condition T4Ct1 = new Condition(t4, "r5g7r8", TransitionCondition.NotNull);
            Condition T4Ct2 = new Condition(t4, "in7", TransitionCondition.IsNull);

            T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);

            GuardMapping grdT4 = new GuardMapping();
            grdT4.condition= T4Ct1;
            grdT4.Activations.add(new Activation(t4, "r5g7r8", TransitionOperation.Move, "r5y7r8"));
            grdT4.Activations.add(new Activation(t4, "yellow", TransitionOperation.SendOverNetwork, "OP7"));
            grdT3.Activations.add(new Activation(t4, "Five", TransitionOperation.DynamicDelay,""));
            t4.GuardMappingList.add(grdT4);



            Condition T4Ct3 = new Condition(t4, "r5g7r8", TransitionCondition.NotNull);
            Condition T4Ct4 = new Condition(t4, "in7", TransitionCondition.NotNull);

            T4Ct3.SetNextCondition(LogicConnector.AND, T4Ct4);

            GuardMapping grdT42 = new GuardMapping();
            grdT42.condition= T4Ct3;
            grdT42.Activations.add(new Activation(t4, "r5g7r8", TransitionOperation.Move, "r5y7r8"));
            grdT42.Activations.add(new Activation(t4, "yellow", TransitionOperation.SendOverNetwork, "OP7"));
            grdT3.Activations.add(new Activation(t4, "Ten", TransitionOperation.DynamicDelay,""));
            t4.GuardMappingList.add(grdT42);

            t4.Delay = 5;
            pn.Transitions.add(t4);


            //----------------------------T5------------------------------------
            PetriTransition t5 = new PetriTransition(pn);
            t5.TransitionName = "T5";
            t5.InputPlaceName.add("r5y7r8");


            Condition T5Ct1 = new Condition(t2, "r5y7r8", TransitionCondition.NotNull);

            GuardMapping grdT5 = new GuardMapping();
            grdT5.condition= T5Ct1;
            grdT5.Activations.add(new Activation(t5, "r5y7r8", TransitionOperation.Move, "r5r7g8"));
            grdT5.Activations.add(new Activation(t5, "red", TransitionOperation.SendOverNetwork, "OP7"));
            grdT5.Activations.add(new Activation(t5, "green", TransitionOperation.SendOverNetwork, "OP8"));

            t5.GuardMappingList.add(grdT5);

            t5.Delay = 5;
            pn.Transitions.add(t5);

            //----------------------------T6------------------------------------
            PetriTransition t6 = new PetriTransition(pn);
            t6.TransitionName = "T6";
            t6.InputPlaceName.add("r5r7g8");
            t6.InputPlaceName.add("in8");


            Condition T6Ct1 = new Condition(t6, "r5r7g8", TransitionCondition.NotNull);
            Condition T6Ct2 = new Condition(t6, "in8", TransitionCondition.IsNull);

            T6Ct1.SetNextCondition(LogicConnector.AND, T6Ct2);

            GuardMapping grdT6 = new GuardMapping();
            grdT6.condition= T6Ct1;
            grdT6.Activations.add(new Activation(t6, "r5r7g8", TransitionOperation.Move, "r5r7y8"));;
            grdT6.Activations.add(new Activation(t6, "yellow", TransitionOperation.SendOverNetwork, "OP8"));
            grdT6.Activations.add(new Activation(t6, "Five", TransitionOperation.DynamicDelay,""));

            t6.GuardMappingList.add(grdT6);


            Condition T6Ct3 = new Condition(t6, "r5r7g8", TransitionCondition.NotNull);
            Condition T6Ct4 = new Condition(t6, "in8", TransitionCondition.NotNull);

            T6Ct3.SetNextCondition(LogicConnector.AND, T6Ct4);

            GuardMapping grdT62 = new GuardMapping();
            grdT62.condition= T6Ct3;
            grdT62.Activations.add(new Activation(t6, "r5r7g8", TransitionOperation.Move, "r5r7y8"));;
            grdT62.Activations.add(new Activation(t6, "yellow", TransitionOperation.SendOverNetwork, "OP8"));
            grdT62.Activations.add(new Activation(t6, "Ten", TransitionOperation.DynamicDelay,""));

            t6.GuardMappingList.add(grdT62);

            t6.Delay = 5;
            pn.Transitions.add(t6);

            //----------------------------T7------------------------------------
            PetriTransition t7 = new PetriTransition(pn);
            t7.TransitionName = "T7";
            t7.InputPlaceName.add("r5r7y8");


            Condition T7Ct1 = new Condition(t7, "r5r7y8", TransitionCondition.NotNull);

            GuardMapping grdT7 = new GuardMapping();
            grdT7.condition= T7Ct1;
            grdT7.Activations.add(new Activation(t7, "r5r7y8", TransitionOperation.Move, "r5r7r8"));;
            grdT7.Activations.add(new Activation(t7, "red", TransitionOperation.SendOverNetwork, "OP8"));

            t7.GuardMappingList.add(grdT7);

            t7.Delay = 5;
            pn.Transitions.add(t7);



            // -------------------------------------------------------------------------------------
            // ----------------------------PN Start-------------------------------------------------
            // -------------------------------------------------------------------------------------

            System.out.println("Exp1 started \n ------------------------------");
            pn.Delay = 2000;
            // pn.Start();

            PetriNetWindow frame = new PetriNetWindow(false);
            frame.petriNet = pn;
            frame.setVisible(true);
        }

}
