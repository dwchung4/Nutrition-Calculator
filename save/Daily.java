public class Daily {
    public double getDailyCalories(String gender, double age, double height, double weight, int activity) {
        double activityIndex = 0.0;
        if (age <= 0.25) {
            return 89*weight-100+175;
        }
        else if (age <= 0.5) {
            return 89*weight-100+56;
        }
        else if (age <= 1) {
            return 89*weight-100+22;
        }
        else if (age <= 3) {
            return 89*weight-100+20;
        }
        else if (gender.equals("Male")) {
            if (age <= 8) {
                if (activity == 0) {
                    activityIndex = 1.0;
                }
                else if (activity == 1) {
                    activityIndex = 1.13;
                }
                else if (activity == 2) {
                    activityIndex = 1.26;
                }
                else if (activity == 3) {
                    activityIndex = 1.42;
                }
                else {
                    throw new IllegalArgumentException("Illegal Activity Level");
                }
                return 88.5-(61.9*age)+activityIndex*(26.7*weight+903*height/100.0)+20;
            }
            else if (age <= 18) {
                if (activity == 0) {
                    activityIndex = 1.0;
                }
                else if (activity == 1) {
                    activityIndex = 1.13;
                }
                else if (activity == 2) {
                    activityIndex = 1.26;
                }
                else if (activity == 3) {
                    activityIndex = 1.42;
                }
                else {
                    throw new IllegalArgumentException("Illegal Activity Level");
                }
                return 88.5-(61.9*age)+activityIndex*(26.7*weight+903*height/100.0)+25;
            }
            else {
                if (activity == 0) {
                    activityIndex = 1.0;
                }
                else if (activity == 1) {
                    activityIndex = 1.11;
                }
                else if (activity == 2) {
                    activityIndex = 1.25;
                }
                else if (activity == 3) {
                    activityIndex = 1.48;
                }
                else {
                    throw new IllegalArgumentException("Illegal Activity Level");
                }
                return 662-(9.53*age)+activityIndex*(15.91*weight+539.6*height/100.0);
            }
        }
        else if (gender.equals("Female")) {
            if (age <= 8) {
                if (activity == 0) {
                    activityIndex = 1.0;
                }
                else if (activity == 1) {
                    activityIndex = 1.16;
                }
                else if (activity == 2) {
                    activityIndex = 1.31;
                }
                else if (activity == 3) {
                    activityIndex = 1.56;
                }
                else {
                    throw new IllegalArgumentException("Illegal Activity Level");
                }
                return 135.3-(30.8*age)+activityIndex*(10.0*weight+934*height/100.0)+20;
            }
            else if (age <= 18) {
                if (activity == 0) {
                    activityIndex = 1.0;
                }
                else if (activity == 1) {
                    activityIndex = 1.16;
                }
                else if (activity == 2) {
                    activityIndex = 1.31;
                }
                else if (activity == 3) {
                    activityIndex = 1.56;
                }
                else {
                    throw new IllegalArgumentException("Illegal Activity Level");
                }
                return 135.3-(30.8*age)+activityIndex*(10.0*weight+934*height/100.0)+25;
            }
            else {
                if (activity == 0) {
                    activityIndex = 1.0;
                }
                else if (activity == 1) {
                    activityIndex = 1.12;
                }
                else if (activity == 2) {
                    activityIndex = 1.27;
                }
                else if (activity == 3) {
                    activityIndex = 1.45;
                }
                else {
                    throw new IllegalArgumentException("Illegal Activity Level");
                }
                return 354-(6.91*age)+activityIndex*(9.36*weight+726*height/100.0);
            }
        }
        else {
            throw new IllegalArgumentException("Wrong Calorie Information");
        }
    }

    public double getDailyCarbohydrate(double age) {
        if (age <= 0.5) {
            return 60.0;
        }
        else if (age <= 1) {
            return 95.0;
        }
        else {
            return 130.0;
        }
    }

    public double getDailyProtein(String gender, double age) {
        if (age <= 0.5) {
            return 9.1;
        }
        else if (age <= 1) {
            return 11.0;
        }
        else if (age <= 3) {
            return 13.0;
        }
        else if (age <= 8) {
            return 19.0;
        }
        else if (age <= 18) {
            if (gender.equals("Male")) {
                return 52.0;
            }
            else if (gender.equals("Female")) {
                return 46.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else {
            if (gender.equals("Male")) {
                return 56.0;
            }
            else if (gender.equals("Female")) {
                return 46.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
    }

    public double getDailySodium(double age) {
        if (age <= 0.5) {
            return 120;
        }
        else if (age <= 1) {
            return 370;
        }
        else if (age <= 3) {
            return 1000;
        }
        else if (age <= 8) {
            return 1200;
        }
        else if (age <= 50) {
            return 1500;
        }
        else if (age <= 70) {
            return 1300;
        }
        else {
            return 1200;
        }
    }

    public double getDailyPotassium(double age) {
        if (age <= 0.5) {
            return 400;
        }
        else if (age <= 1) {
            return 700;
        }
        else if (age <= 3) {
            return 3000;
        }
        else if (age <= 8) {
            return 3800;
        }
        else if (age <= 13) {
            return 4500;
        }
        else {
            return 4700;
        }
    }

    public double getDailyFiber(String gender, double age) {
        if (age <= 1) {
            return Double.POSITIVE_INFINITY;
        }
        else if (age <= 3) {
            return 19.0;
        }
        else if (age <= 8) {
            return 25.0;
        }
        else if (age <= 13) {
            if (gender.equals("Male")) {
                return 31.0;
            }
            else if (gender.equals("Female")) {
                return 26.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else if (age <= 18) {
            if (gender.equals("Male")) {
                return 38.0;
            }
            else if (gender.equals("Female")) {
                return 26.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else if (age <= 50) {
            if (gender.equals("Male")) {
                return 38.0;
            }
            else if (gender.equals("Female")) {
                return 25.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else {
            if (gender.equals("Male")) {
                return 30.0;
            }
            else if (gender.equals("Female")) {
                return 21.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
    }

    public double getDailyVitaminA(String gender, double age) {
        if (age <= 0.5) {
            return 400.0;
        }
        else if (age <= 1) {
            return 500.0;
        }
        else if (age <= 3) {
            return 300.0;
        }
        else if (age <= 8) {
            return 400.0;
        }
        else if (age <= 13) {
            return 600.0;
        }
        else {
            if (gender.equals("Male")) {
                return 900.0;
            }
            else if (gender.equals("Female")) {
                return 700.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
    }

    public double getDailyVitaminC(String gender, double age) {
        if (age <= 0.5) {
            return 40.0;
        }
        else if (age <= 1) {
            return 50.0;
        }
        else if (age <= 3) {
            return 15.0;
        }
        else if (age <= 8) {
            return 25.0;
        }
        else if (age <= 13) {
            return 45.0;
        }
        else if (age <= 18) {
            if (gender.equals("Male")) {
                return 75.0;
            }
            else if (gender.equals("Female")) {
                return 65.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else {
            if (gender.equals("Male")) {
                return 90.0;
            }
            else if (gender.equals("Female")) {
                return 75.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
    }

    public double getDailyCalcium(String gender, double age) {
        if (age <= 0.5) {
            return 200.0;
        }
        else if (age <= 1) {
            return 260.0;
        }
        else if (age <= 3) {
            return 700.0;
        }
        else if (age <= 8) {
            return 1000.0;
        }
        else if (age <= 18) {
            return 1300.0;
        }
        else if (age <= 50) {
            return 1000.0;
        }
        else if (age <= 70) {
            if (gender.equals("Male")) {
                return 1000.0;
            }
            else if (gender.equals("Female")) {
                return 1200.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else {
            return 1200.0;
        }
    }

    public double getDailyIron(String gender, double age) {
        if (age <= 0.5) {
            return 0.27;
        }
        else if (age <= 1) {
            return 11.0;
        }
        else if (age <= 3) {
            return 7.0;
        }
        else if (age <= 8) {
            return 10.0;
        }
        else if (age <= 13) {
            return 8.0;
        }
        else if (age <= 18) {
            if (gender.equals("Male")) {
                return 11.0;
            }
            else if (gender.equals("Female")) {
                return 15.0;
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
        else {
            if (gender.equals("Male")) {
                return 8.0;
            }
            else if (gender.equals("Female")) {
                if (age <= 50) {
                    return 18.0;
                }
                else {
                    return 8.0;
                }
            }
            else {
                throw new IllegalArgumentException("Wrong Gender Information");
            }
        }
    }
}
