package OETPN.Project_2_Intersections;

import Components.*;
import DataObjects.DataCar;
import DataObjects.DataCarQueue;
import DataObjects.DataString;
import DataObjects.DataTransfer;
import DataOnly.TransferOperation;
import Enumerations.LogicConnector;
import Enumerations.TransitionCondition;
import Enumerations.TransitionOperation;

public class Second_Intersection {
    public static void main(String[] args) {

        PetriNet pn = new PetriNet();
        pn.PetriNetName = "Second Intersection";
        pn.NetworkPort = 1081;

        DataString green = new DataString();
        green.Printable = false;
        green.SetName("green");
        green.SetValue("green");
        pn.ConstantPlaceList.add(green);

        DataString full = new DataString();
        full.SetName("full");
        full.SetValue("full");
        pn.ConstantPlaceList.add(full);

        // --------------------------------Str.Liege in1------------------------------------
        // -------------------------------------------------------------------------------------

        DataCar pa5 = new DataCar();
        pa5.SetName("P_a5");
        pn.PlaceList.add(pa5);

        DataCarQueue px5 = new DataCarQueue();
        px5.SetName("P_x5");
        px5.Value.Size= 3;
        pn.PlaceList.add(px5);

        DataCar pb5 = new DataCar();
        pb5.SetName("P_b5");
        pn.PlaceList.add(pb5);

        DataString ptl5 = new DataString();
        ptl5.SetName("P_TL5");
        pn.PlaceList.add(ptl5);

        DataTransfer op5 = new DataTransfer();
        op5.SetName("OP5");
        op5.Value = new TransferOperation("localhost", "1084", "in5");
        pn.PlaceList.add(op5);

        // --------------------------------Str.Liege in2------------------------------------
        // -------------------------------------------------------------------------------------

        DataCar pa7 = new DataCar();
        pa7.SetName("P_a7");
        pn.PlaceList.add(pa7);

        DataCarQueue px7 = new DataCarQueue();
        px7.SetName("P_x7");
        px7.Value.Size= 3;
        pn.PlaceList.add(px7);

        DataCar pb7= new DataCar();
        pb7.SetName("P_b7");
        pn.PlaceList.add(pb7);

        DataString ptl7 = new DataString();
        ptl7.SetName("P_TL7");
        pn.PlaceList.add(ptl7);

        DataTransfer op7 = new DataTransfer();
        op7.SetName("OP7");
        op7.Value = new TransferOperation("localhost", "1084", "in7");
        pn.PlaceList.add(op7);

        // --------------------------------Str. Orsova in------------------------------------
        // -------------------------------------------------------------------------------------

        DataCar pa8 = new DataCar();
        pa8.SetName("P_a8");
        pn.PlaceList.add(pa7);

        DataCarQueue px8 = new DataCarQueue();
        px8.SetName("P_x8");
        px8.Value.Size= 3;
        pn.PlaceList.add(px8);

        DataCar pb8= new DataCar();
        pb8.SetName("P_b8");
        pn.PlaceList.add(pb8);

        DataString ptl8 = new DataString();
        ptl8.SetName("P_TL8");
        pn.PlaceList.add(ptl8);

        DataTransfer op8 = new DataTransfer();
        op8.SetName("OP8");
        op8.Value = new TransferOperation("localhost", "1084", "in8");
        pn.PlaceList.add(op8);

        // --------------------------------Str.Liege Exit 1------------------------------------
        // -------------------------------------------------------------------------------------

        DataCarQueue po5 = new DataCarQueue();
        po5.Value.Size = 3;
        po5.SetName("P_o5");
        pn.PlaceList.add(po5);

        DataCar po5e = new DataCar();
        po5e.SetName("P_o5e");
        pn.PlaceList.add(po5e);

        DataTransfer P_o5N = new DataTransfer();
        P_o5N.SetName("P_o5N");
        P_o5N.Value = new TransferOperation("localhost", "1080", "P_a4");
        pn.PlaceList.add(P_o5N);

        // --------------------------------Str.Liege Exit 2------------------------------------
        // -------------------------------------------------------------------------------------

        DataCarQueue po7 = new DataCarQueue();
        po7.Value.Size= 3;
        po7.SetName("P_o7");
        pn.PlaceList.add(po7);

        DataCar po7e = new DataCar();
        po7e.SetName("P_o7e");
        pn.PlaceList.add(po7e);


        // --------------------------------Str.Orsova Exit-------------------------------
        // ------------------------------------------------------------------------------
        DataCarQueue po6 = new DataCarQueue();
        po6.SetName("P_o6");
        po6.Value.Size= 3;
        pn.PlaceList.add(po6);

        DataCar po6e = new DataCar();
        po6e.SetName("P_o6e");
        pn.PlaceList.add(po6e);


        // -------------------------------------------------------------------------------------------
        // --------------------------------Intersection-----------------------------------------------
        // -------------------------------------------------------------------------------------------

        DataCarQueue pI2 = new DataCarQueue();
        pI2.Value.Size = 3;
        pI2.SetName("P_I2");
        pn.PlaceList.add(pI2);


        // T1 ------------------------------------------------
        PetriTransition t1 = new PetriTransition(pn);
        t1.TransitionName = "T_u5";
        t1.InputPlaceName.add("P_a5");
        t1.InputPlaceName.add("P_x5");

        Condition T1Ct1 = new Condition(t1, "P_a5", TransitionCondition.NotNull);
        Condition T1Ct2 = new Condition(t1, "P_x5", TransitionCondition.CanAddCars);
        T1Ct1.SetNextCondition(LogicConnector.AND, T1Ct2);

        GuardMapping grdT1 = new GuardMapping();
        grdT1.condition = T1Ct1;
        grdT1.Activations.add(new Activation(t1, "P_a5", TransitionOperation.AddElement, "P_x5"));
        t1.GuardMappingList.add(grdT1);


        Condition T1Ct3 = new Condition(t1, "P_a5", TransitionCondition.NotNull);
        Condition T1Ct4 = new Condition(t1, "P_x5", TransitionCondition.CanNotAddCars);
        T1Ct3.SetNextCondition(LogicConnector.AND, T1Ct4);

        GuardMapping grdT112 = new GuardMapping();
        grdT112.condition= T1Ct3;
        grdT112.Activations.add(new Activation(t1, "full", TransitionOperation.SendOverNetwork, "OP5"));
        grdT112.Activations.add(new Activation(t1, "P_a5", TransitionOperation.Move, "P_a5"));
        t1.GuardMappingList.add(grdT112);

        t1.Delay = 0;
        pn.Transitions.add(t1);

        // T2 ------------------------------------------------
        PetriTransition t2 = new PetriTransition(pn);
        t2.TransitionName = "T_e5";
        t2.InputPlaceName.add("P_x5");
        t2.InputPlaceName.add("P_TL5");


        Condition T2Ct1 = new Condition(t2, "P_TL5", TransitionCondition.Equal,"green");
        Condition T2Ct2 = new Condition(t2, "P_x5", TransitionCondition.HaveCar);
        T2Ct1.SetNextCondition(LogicConnector.AND, T2Ct2);

        GuardMapping grdT2 = new GuardMapping();
        grdT2.condition= T2Ct1;
        grdT2.Activations.add(new Activation(t2, "P_x5", TransitionOperation.PopElementWithoutTarget, "P_b5"));
        grdT2.Activations.add(new Activation(t2, "P_TL5", TransitionOperation.Move, "P_TL5"));

        t2.GuardMappingList.add(grdT2);

        t2.Delay = 0;
        pn.Transitions.add(t2);

        // T3 ------------------------------------------------
        PetriTransition t3 = new PetriTransition(pn);
        t3.TransitionName = "T_u7";
        t3.InputPlaceName.add("P_a7");
        t3.InputPlaceName.add("P_x7");

        Condition T3Ct1 = new Condition(t3, "P_a7", TransitionCondition.NotNull);
        Condition T3Ct2 = new Condition(t3, "P_x7", TransitionCondition.CanAddCars);
        T3Ct1.SetNextCondition(LogicConnector.AND, T3Ct2);

        GuardMapping grdT3 = new GuardMapping();
        grdT3.condition = T3Ct1;
        grdT3.Activations.add(new Activation(t3, "P_a7", TransitionOperation.AddElement, "P_x7"));
        t3.GuardMappingList.add(grdT3);


        Condition T3Ct3 = new Condition(t3, "P_a7", TransitionCondition.NotNull);
        Condition T3Ct4 = new Condition(t3, "P_x7", TransitionCondition.CanNotAddCars);
        T3Ct3.SetNextCondition(LogicConnector.AND, T3Ct4);

        GuardMapping grdT32 = new GuardMapping();
        grdT32.condition= T3Ct3;
        grdT32.Activations.add(new Activation(t3, "full", TransitionOperation.SendOverNetwork, "OP7"));
        grdT32.Activations.add(new Activation(t3, "P_a7", TransitionOperation.Move, "P_a7"));
        t3.GuardMappingList.add(grdT32);

        t3.Delay = 0;
        pn.Transitions.add(t3);

        // T4 ------------------------------------------------
        PetriTransition t4 = new PetriTransition(pn);
        t4.TransitionName = "T_e7";
        t4.InputPlaceName.add("P_x7");
        t4.InputPlaceName.add("P_TL7");

        Condition T4Ct1 = new Condition(t4, "P_TL7", TransitionCondition.Equal, "green");
        Condition T4Ct2 = new Condition(t4, "P_x7", TransitionCondition.HaveCar);
        T4Ct1.SetNextCondition(LogicConnector.AND, T4Ct2);

        GuardMapping grdT4 = new GuardMapping();
        grdT4.condition = T4Ct1;
        grdT4.Activations.add(new Activation(t4, "P_x7", TransitionOperation.PopElementWithoutTarget, "P_b7"));
        grdT4.Activations.add(new Activation(t4, "P_TL7", TransitionOperation.Move, "P_TL7"));
        t4.GuardMappingList.add(grdT2);

        t4.Delay = 0;
        pn.Transitions.add(t4);

        // T5 ------------------------------------------------
        PetriTransition t5 = new PetriTransition(pn);
        t5.TransitionName = "T_u8";
        t5.InputPlaceName.add("P_a8");
        t5.InputPlaceName.add("P_x8");

        Condition T5Ct1 = new Condition(t5, "P_a8", TransitionCondition.NotNull);
        Condition T5Ct2 = new Condition(t5, "P_x8", TransitionCondition.CanAddCars);
        T5Ct1.SetNextCondition(LogicConnector.AND, T5Ct2);

        GuardMapping grdT5 = new GuardMapping();
        grdT5.condition = T5Ct1;
        grdT5.Activations.add(new Activation(t5, "P_a8", TransitionOperation.AddElement, "P_x8"));
        t5.GuardMappingList.add(grdT5);

        Condition T5Ct3 = new Condition(t5, "P_a8", TransitionCondition.NotNull);
        Condition T5Ct4 = new Condition(t5, "P_x8", TransitionCondition.CanNotAddCars);
        T5Ct3.SetNextCondition(LogicConnector.AND, T5Ct4);

        GuardMapping grdT52 = new GuardMapping();
        grdT52.condition= T5Ct3;
        grdT52.Activations.add(new Activation(t5, "full", TransitionOperation.SendOverNetwork, "OP8"));
        grdT52.Activations.add(new Activation(t5, "P_a8", TransitionOperation.Move, "P_a8"));
        t5.GuardMappingList.add(grdT52);

        t5.Delay = 0;
        pn.Transitions.add(t5);

        // T6 ------------------------------------------------
        PetriTransition t6 = new PetriTransition(pn);
        t6.TransitionName = "T_e8";
        t6.InputPlaceName.add("P_x8");
        t6.InputPlaceName.add("P_TL8");

        Condition T6Ct1 = new Condition(t6, "P_TL8", TransitionCondition.Equal, "green");
        Condition T6Ct2 = new Condition(t6, "P_x8", TransitionCondition.HaveCar);
        T6Ct1.SetNextCondition(LogicConnector.AND, T6Ct2);

        GuardMapping grdT6 = new GuardMapping();
        grdT6.condition = T6Ct1;
        grdT6.Activations.add(new Activation(t6, "P_x8", TransitionOperation.PopElementWithoutTarget, "P_b8"));
        grdT6.Activations.add(new Activation(t6, "P_TL8", TransitionOperation.Move, "P_TL8"));
        t6.GuardMappingList.add(grdT6);

        t6.Delay = 0;
        pn.Transitions.add(t6);

        // T7----------------------------------------------------------------

        PetriTransition t7 = new PetriTransition(pn);
        t7.TransitionName = "T_g5e";
        t7.InputPlaceName.add("P_o5");

        Condition T7Ct1 = new Condition(t7, "P_o5", TransitionCondition.HaveCar);

        GuardMapping grdT7 = new GuardMapping();
        grdT7.condition = T7Ct1;
        grdT7.Activations.add(new Activation(t7, "P_o5", TransitionOperation.PopElementWithoutTarget, "P_o5e"));
        t7.GuardMappingList.add(grdT7);

        t7.Delay = 0;
        pn.Transitions.add(t7);

        // T8----------------------------------------------------------------

        PetriTransition t8 = new PetriTransition(pn);
        t8.TransitionName = "T_g6e";
        t8.InputPlaceName.add("P_o6");

        Condition T8Ct1 = new Condition(t8, "P_o6", TransitionCondition.HaveCar);

        GuardMapping grdT8 = new GuardMapping();
        grdT8.condition = T8Ct1;
        grdT8.Activations.add(new Activation(t8, "P_o6", TransitionOperation.PopElementWithoutTarget, "P_o6e"));
        t8.GuardMappingList.add(grdT8);

        t8.Delay = 0;
        pn.Transitions.add(t8);

        // T9----------------------------------------------------------------

        PetriTransition t9 = new PetriTransition(pn);
        t9.TransitionName = "T_g7e";
        t9.InputPlaceName.add("P_o7");

        Condition T9Ct1 = new Condition(t9, "P_o7", TransitionCondition.HaveCar);

        GuardMapping grdT9 = new GuardMapping();
        grdT9.condition = T9Ct1;
        grdT9.Activations.add(new Activation(t9, "P_o7", TransitionOperation.PopElementWithoutTarget, "P_o7e"));
        t9.GuardMappingList.add(grdT9);

        t9.Delay = 0;
        pn.Transitions.add(t9);

        // T_g5N----------------------------------------------------------------

        PetriTransition T_g5N = new PetriTransition(pn);
        T_g5N.TransitionName = "T_g5N";
        T_g5N.InputPlaceName.add("P_o5e");

        Condition t_g5NCt1 = new Condition(T_g5N, "P_o5e", TransitionCondition.NotNull);

        GuardMapping grdt_g5N = new GuardMapping();
        grdt_g5N.condition = t_g5NCt1;
        grdt_g5N.Activations.add(new Activation(T_g5N, "P_o5e", TransitionOperation.SendOverNetwork, "P_o5N"));
        T_g5N.GuardMappingList.add(grdt_g5N);

        T_g5N.Delay = 0;
        pn.Transitions.add(T_g5N);

        // --------------------------------------FirstPart-------------------------------------------

        // T10 ------------------------------------------------
        PetriTransition t10 = new PetriTransition(pn);
        t10.TransitionName = "T_i5";
        t10.InputPlaceName.add("P_b5");
        t10.InputPlaceName.add("P_I2");

        Condition T10Ct1 = new Condition(t10, "P_b5", TransitionCondition.NotNull);
        Condition T10Ct2 = new Condition(t10, "P_I2", TransitionCondition.CanAddCars);
        T10Ct1.SetNextCondition(LogicConnector.AND, T10Ct2);

        GuardMapping grdT10 = new GuardMapping();
        grdT10.condition = T10Ct1;
        grdT10.Activations.add(new Activation(t10, "P_b5", TransitionOperation.AddElement, "P_I2"));
        t10.GuardMappingList.add(grdT10);

        t10.Delay = 0;
        pn.Transitions.add(t10);

        // T11-----------------------------------------------------------
        PetriTransition t11 = new PetriTransition(pn);
        t11.TransitionName = "T_g5";
        t11.InputPlaceName.add("P_I2");
        t11.InputPlaceName.add("P_o5");

        Condition T11Ct1 = new Condition(t11, "P_I2", TransitionCondition.HaveCarForMe);
        Condition T11Ct2 = new Condition(t11, "P_o5", TransitionCondition.CanAddCars);
        T11Ct1.SetNextCondition(LogicConnector.AND, T11Ct2);

        GuardMapping grdT11 = new GuardMapping();
        grdT11.condition = T11Ct1;
        grdT11.Activations.add(new Activation(t11, "P_I2", TransitionOperation.PopElementWithTargetToQueue, "P_o5"));
        t11.GuardMappingList.add(grdT11);

        t11.Delay = 0;
        pn.Transitions.add(t11);

        // ---------------------------------SecondPart-------------------------------------------

        // T12 ------------------------------------------------
        PetriTransition t12 = new PetriTransition(pn);
        t12.TransitionName = "T_g6";
        t12.InputPlaceName.add("P_I2");
        t12.InputPlaceName.add("P_o6");

        Condition T12Ct1 = new Condition(t12, "P_I2", TransitionCondition.HaveCarForMe);
        Condition T12Ct2 = new Condition(t12, "P_o6", TransitionCondition.CanAddCars);
        T12Ct1.SetNextCondition(LogicConnector.AND, T12Ct2);

        GuardMapping grdT12 = new GuardMapping();
        grdT12.condition = T12Ct1;
        grdT12.Activations.add(new Activation(t12, "P_I2", TransitionOperation.PopElementWithTargetToQueue, "P_o6"));
        t12.GuardMappingList.add(grdT12);

        t12.Delay = 0;
        pn.Transitions.add(t12);

        // ----------------------ThirdPart----------------------------------------------------------------

        // T13 ------------------------------------------------
        PetriTransition t13 = new PetriTransition(pn);
        t13.TransitionName = "T_i7";
        t13.InputPlaceName.add("P_b7");
        t13.InputPlaceName.add("P_I2");

        Condition T13Ct1 = new Condition(t13, "P_b7", TransitionCondition.NotNull);
        Condition T13Ct2 = new Condition(t13, "P_I2", TransitionCondition.CanAddCars);
        T13Ct1.SetNextCondition(LogicConnector.AND, T13Ct2);

        GuardMapping grdT13 = new GuardMapping();
        grdT13.condition = T13Ct1;
        grdT13.Activations.add(new Activation(t13, "P_b7", TransitionOperation.AddElement, "P_I2"));
        t13.GuardMappingList.add(grdT13);

        t13.Delay = 0;
        pn.Transitions.add(t13);

        // T14---------------------------------------------------------

        PetriTransition t14 = new PetriTransition(pn);
        t14.TransitionName = "T_g7";
        t14.InputPlaceName.add("P_I2");
        t14.InputPlaceName.add("P_o7");

        Condition T14Ct1 = new Condition(t14, "P_I2", TransitionCondition.HaveCarForMe);
        Condition T14Ct2 = new Condition(t14, "P_o7", TransitionCondition.CanAddCars);
        T14Ct1.SetNextCondition(LogicConnector.AND, T14Ct2);

        GuardMapping grdT14 = new GuardMapping();
        grdT14.condition = T14Ct1;
        grdT14.Activations.add(new Activation(t14, "P_I2", TransitionOperation.PopElementWithTargetToQueue, "P_o7"));
        t14.GuardMappingList.add(grdT14);

        t14.Delay = 0;
        pn.Transitions.add(t14);

        // -------------------------------------FourthPart------------------------------------------

        // T15 ------------------------------------------------
        PetriTransition t15 = new PetriTransition(pn);
        t15.TransitionName = "T_i8";
        t15.InputPlaceName.add("P_b8");
        t15.InputPlaceName.add("P_I2");

        Condition T15Ct1 = new Condition(t15, "P_b8", TransitionCondition.NotNull);
        Condition T15Ct2 = new Condition(t15, "P_I2", TransitionCondition.CanAddCars);
        T15Ct1.SetNextCondition(LogicConnector.AND, T15Ct2);

        GuardMapping grdT15 = new GuardMapping();
        grdT15.condition = T15Ct1;
        grdT15.Activations.add(new Activation(t15, "P_b8", TransitionOperation.PopElementWithoutTarget, "P_I2"));
        t15.GuardMappingList.add(grdT15);

        t15.Delay = 0;
        pn.Transitions.add(t15);

        // -------------------------------------------------------------------------------------
        // ----------------------------PNStart-------------------------------------------------
        // -------------------------------------------------------------------------------------

        System.out.println("Exp1 started \n ------------------------------");
        pn.Delay = 2000;
        // pn.Start();

        PetriNetWindow frame = new PetriNetWindow(false);
        frame.petriNet = pn;
        frame.setVisible(true);


    }
}
