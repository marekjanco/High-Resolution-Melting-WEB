package cz.muni.fi.hrm;

import cz.muni.fi.hrm.entity.ErrorMargin;
import cz.muni.fi.hrm.entity.RefCurve;
import cz.muni.fi.hrm.service.RefCurveService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class SampleDataServiceImpl implements SampleDataService {
    @Inject
    private RefCurveService refCurveService;

    @Override
    public void loadData() {
        this.newRefCurve("temperature", "t", "temperature - x axis values",
                new Double[]{74.90759295, 74.94735812, 74.98712329, 75.02688845, 75.06665362, 75.10641879, 75.14618395, 75.18594912, 75.22571429, 75.26547945, 75.30524462, 75.34500978, 75.38477495, 75.42454012, 75.46430528, 75.50407045, 75.54383562, 75.58360078, 75.62336595, 75.66313112, 75.70289628, 75.74266145, 75.78242661, 75.82219178, 75.86195695, 75.90172211, 75.94148728, 75.98125245, 76.02101761, 76.06078278, 76.10054795, 76.14031311, 76.18007828, 76.21984344, 76.25960861, 76.29937378, 76.33913894, 76.37890411, 76.41866928, 76.45843444, 76.49819961, 76.53796477, 76.57772994, 76.61749511, 76.65726027, 76.69702544, 76.73679061, 76.77655577, 76.81632094, 76.85608611, 76.89585127, 76.93561644, 76.9753816, 77.01514677, 77.05491194, 77.0946771, 77.13444227, 77.17420744, 77.2139726, 77.25373777, 77.29350294, 77.3332681, 77.37303327, 77.41279843, 77.4525636, 77.49232877, 77.53209393, 77.5718591, 77.61162427, 77.65138943, 77.6911546, 77.73091977, 77.77068493, 77.8104501, 77.85021526, 77.88998043, 77.9297456, 77.96951076, 78.00927593, 78.0490411, 78.08880626, 78.12857143, 78.16833659, 78.20810176, 78.24786693, 78.28763209, 78.32739726, 78.36716243, 78.40692759, 78.44669276, 78.48645793, 78.52622309, 78.56598826, 78.60575342, 78.64551859, 78.68528376, 78.72504892, 78.76481409, 78.80457926, 78.84434442, 78.88410959, 78.92387476, 78.96363992, 79.00340509, 79.04317025, 79.08293542, 79.12270059, 79.16246575, 79.20223092, 79.24199609, 79.28176125, 79.32152642, 79.36129159, 79.40105675, 79.44082192, 79.48058708, 79.52035225, 79.56011742, 79.59988258, 79.63964775, 79.67941292, 79.71917808, 79.75894325, 79.79870841, 79.83847358, 79.87823875, 79.91800391, 79.95776908, 79.99753425, 80.03729941, 80.07706458, 80.11682975, 80.15659491, 80.19636008, 80.23612524, 80.27589041, 80.31565558, 80.35542074, 80.39518591, 80.43495108, 80.47471624, 80.51448141, 80.55424658, 80.59401174, 80.63377691, 80.67354207, 80.71330724, 80.75307241, 80.79283757, 80.83260274, 80.87236791, 80.91213307, 80.95189824, 80.99166341, 81.03142857, 81.07119374, 81.1109589, 81.15072407, 81.19048924, 81.2302544},
                null);
        this.newRefCurve("Trichinella spiralis", "TS", "Trichinella spiralis",
                new Double[]{-0.000677144, -0.008282757, -0.013933874, -0.016439405, -0.015487082, -0.009511936, 0.000443725, 0.013886527, 0.023795722, 0.035386219, 0.053383137, 0.07411648, 0.097077127, 0.121572214, 0.147023296, 0.173375941, 0.200854494, 0.23006583, 0.26221989, 0.298337681, 0.339405436, 0.386400291, 0.440125013, 0.501256912, 0.570565727, 0.648833115, 0.736669014, 0.834678664, 0.943124063, 1.061789894, 1.190295718, 1.327830794, 1.473758853, 1.627335531, 1.787822191, 1.954741218, 2.127672658, 2.306550264, 2.492121957, 2.684697875, 2.875970778, 3.077392156, 3.291331574, 3.521626144, 3.771885457, 4.044830007, 4.343016493, 4.667915608, 5.019889587, 5.398518468, 5.800935382, 6.222820171, 6.659602607, 7.105364747, 7.554825047, 8.003400223, 8.444713647, 8.871528549, 9.277672889, 9.657155481, 10.00476701, 10.31368975, 10.585575, 10.82079499, 11.02283432, 11.19403742, 11.3386921, 11.45747404, 11.54639472, 11.60156287, 11.61542048, 11.58351051, 11.50258768, 11.364662, 11.16622074, 10.90319869, 10.56959891, 10.16836262, 9.706981989, 9.191134588, 8.625527006, 8.012867883, 7.356789848, 6.669215824, 5.964506286, 5.258210568, 4.56724611, 3.902524113, 3.273197187, 2.68856871, 2.156125391, 1.683155168, 1.274645364, 0.929577025, 0.644340679, 0.413347729, 0.230149619, 0.08880117, -0.015396221, -0.088941362, -0.138455527, -0.169618483, -0.18728626, -0.194893157, -0.194872939, -0.189281327, -0.17931498, -0.166086694, -0.151055416, -0.135183523, -0.119514571, -0.104705147, -0.090712049, -0.077744024, -0.066080247, -0.055891475, -0.04734632, -0.040368538, -0.034819553, -0.030453424, -0.026402105, -0.023257279, -0.02079066, -0.018669913, -0.016237531, -0.013836317, -0.011480102, -0.00912644, -0.006785833, -0.004547701, -0.002593257, -0.001065602, 1.00521E-05, 0.000688066, 0.000952522, 0.000780012, 0.00021637, 9.32871E-05, -5.94033E-05, -0.000356854, -0.000685995, -0.000928046, -0.000869539, -0.0002422, 0.000107887, 0.000330134, 0.000673001, 0.001138312, 0.002139739, 0.000273803, -0.001287428, -0.00305306, -0.00499083, -0.006656649, -0.008293064, -0.009656369, -0.010331435, -0.01130785, -0.012402732, 0.0},
                new Double[]{0.031357002, 0.031968345, 0.032834068, 0.031686869, 0.026313546, 0.019894507, 0.014376185, 0.013348694, 0.019311916, 0.028878066, 0.039347055, 0.049385653, 0.058201837, 0.065599042, 0.072161286, 0.078867056, 0.086697371, 0.096324654, 0.108131407, 0.12223701, 0.138346559, 0.156007789, 0.17483095, 0.194610802, 0.215071171, 0.235750774, 0.256012008, 0.274956575, 0.291781308, 0.306283843, 0.318460172, 0.328428924, 0.336494752, 0.342944679, 0.34813502, 0.352363568, 0.355952867, 0.359295555, 0.363005626, 0.367965611, 0.37517083, 0.385644284, 0.40021081, 0.419519133, 0.444415641, 0.47568605, 0.513120648, 0.555025115, 0.598898827, 0.642360539, 0.683593432, 0.720992726, 0.752755157, 0.776823578, 0.792055728, 0.799070932, 0.799103294, 0.793147559, 0.782452604, 0.769278872, 0.757333989, 0.751220924, 0.755101883, 0.770908906, 0.798430168, 0.83644601, 0.884270224, 0.941341063, 1.005044152, 1.069581617, 1.126843823, 1.169444419, 1.191841664, 1.189992727, 1.162165643, 1.109831213, 1.038926968, 0.957922196, 0.874438302, 0.793793099, 0.71798174, 0.646712606, 0.579625469, 0.51699289, 0.459121722, 0.405740511, 0.356469846, 0.310816396, 0.268041407, 0.228338484, 0.192856834, 0.162448685, 0.13652261, 0.113266499, 0.091055295, 0.069235287, 0.048243056, 0.028905628, 0.011771744, 0.003273204, 0.015848131, 0.026348061, 0.034750641, 0.04108712, 0.045435769, 0.047954338, 0.04890152, 0.048507671, 0.047086815, 0.044909246, 0.042207531, 0.039133804, 0.035795413, 0.032395239, 0.029097039, 0.026075514, 0.023486041, 0.021396706, 0.019823235, 0.018661359, 0.017742384, 0.016944526, 0.016217156, 0.015548168, 0.014918463, 0.014391328, 0.014009777, 0.01364502, 0.013109177, 0.012292967, 0.011197689, 0.009891854, 0.008435603, 0.006933841, 0.005559621, 0.004538574, 0.004010688, 0.003862458, 0.003810604, 0.003664914, 0.003412213, 0.003144736, 0.002944822, 0.002802392, 0.002677805, 0.00257572, 0.002479402, 0.002399092, 0.002440688, 0.002654824, 0.002910624, 0.003011193, 0.002899074, 0.002478097, 0.001889397, 0.001404443, 0.000838384, 0.000553829, 0.001136452, 0.0});
        this.newRefCurve(
                "Trichinella nativa", "TN", "Trichinella nativa",
                new Double[]{0.141103351, 0.126911172, 0.120884602, 0.105741601, 0.090564431, 0.078495471, 0.06981478, 0.06420569, 0.064155692, 0.068992731, 0.082651245, 0.097122497, 0.10223807, 0.101882984, 0.09310028, 0.102272223, 0.114984064, 0.131308625, 0.152009045, 0.177899863, 0.210114448, 0.250804572, 0.30237893, 0.363511832, 0.435207217, 0.518804507, 0.615710058, 0.727493426, 0.855673209, 1.001467091, 1.165881234, 1.349189775, 1.551969078, 1.774959175, 2.019213905, 2.285948309, 2.576102168, 2.890802574, 3.232014411, 3.60227873, 4.003818804, 4.438866684, 4.911174623, 5.426057461, 5.988240548, 6.602061753, 7.272669191, 8.005413188, 8.804959657, 9.674233164, 10.61297604, 11.6196297, 12.69134065, 13.82213591, 15.00371685, 16.2253408, 17.47372636, 18.73249237, 19.98112119, 21.19570624, 22.35374178, 23.43241357, 24.40787541, 25.25982722, 25.97203577, 26.52139743, 26.90851223, 27.12747513, 27.16912215, 27.02590885, 26.67853773, 26.14589449, 25.43848702, 24.56677308, 23.54638777, 22.39066041, 21.1114275, 19.72934682, 18.27026404, 16.75869627, 15.21698835, 13.66622092, 12.12679388, 10.62237471, 9.175929446, 7.808882035, 6.540325965, 5.382527049, 4.34281317, 3.425737607, 2.632084513, 1.961280444, 1.409715464, 0.968324065, 0.625756323, 0.368443353, 0.182555343, 0.055647487, -0.02550063, -0.071613625, -0.092007612, -0.095363711, -0.088038094, -0.074589998, -0.058136569, -0.040988085, -0.024249037, -0.008716135, 0.004916579, 0.016430192, 0.025410159, 0.031820174, 0.036250424, 0.038893197, 0.039892829, 0.039421282, 0.037604087, 0.034741478, 0.031125731, 0.027085088, 0.02301776, 0.019181672, 0.016797664, 0.014997978, 0.0134558, 0.012247871, 0.010999598, 0.009658903, 0.008787879, 0.008445134, 0.008450153, 0.008616843, 0.008820955, 0.008948016, 0.008814851, 0.008311518, 0.007466021, 0.006460559, 0.005484089, 0.004591293, 0.003843243, 0.003269477, 0.003076621, 0.003333705, 0.003567505, 0.003764045, 0.003643341, 0.003420399, 0.003191257, 0.000830369, -0.000986773, -0.002881039, -0.004889271, -0.006589771, -0.007696295, -0.008998001, -0.010522046, -0.012218362, -0.014224656, 0.0},
                new Double[]{0.186209105, 0.183767091, 0.178333203, 0.179065746, 0.179006418, 0.175892384, 0.169192226, 0.158742228, 0.144487133, 0.126491807, 0.11700539, 0.100729337, 0.063427924, 0.035344412, 0.057733432, 0.101333339, 0.147674216, 0.194035891, 0.239925223, 0.285476599, 0.330907768, 0.376639072, 0.423553778, 0.472740578, 0.525198461, 0.581406874, 0.641381489, 0.704844426, 0.770890943, 0.838232211, 0.905791596, 0.973012973, 1.039124266, 1.10299642, 1.16382899, 1.221218522, 1.275373119, 1.327182372, 1.377372288, 1.426654435, 1.476396963, 1.528647294, 1.585057703, 1.646929657, 1.715946249, 1.794040598, 1.883035901, 1.984116368, 2.097676576, 2.224217124, 2.363871893, 2.514784821, 2.672858073, 2.833468283, 2.991431251, 3.138500181, 3.263842768, 3.357051585, 3.410785064, 3.421074898, 3.38650051, 3.306273268, 3.182387614, 3.023284343, 2.840499821, 2.644437932, 2.444353328, 2.249890282, 2.07051261, 1.915471258, 1.790962035, 1.697456798, 1.630692455, 1.583647778, 1.54904653, 1.52099809, 1.494261038, 1.463358641, 1.423854965, 1.373858065, 1.313313747, 1.243292955, 1.16526278, 1.080908034, 0.993125293, 0.905545252, 0.82125517, 0.742021046, 0.667960348, 0.597969919, 0.530431467, 0.464146822, 0.399000814, 0.334887692, 0.271907287, 0.210179574, 0.149621097, 0.090972127, 0.035967235, 0.01514337, 0.058813833, 0.096691046, 0.128215796, 0.153331791, 0.172170879, 0.185092495, 0.192527834, 0.19508032, 0.19350626, 0.188730118, 0.181662282, 0.173146639, 0.163803131, 0.154034814, 0.144141401, 0.13441091, 0.125098299, 0.116413221, 0.108467816, 0.101271515, 0.094728126, 0.088669958, 0.082878883, 0.077124693, 0.071260632, 0.06527786, 0.059266027, 0.05333652, 0.047620204, 0.042302505, 0.03763046, 0.03379472, 0.030767843, 0.02835655, 0.026350962, 0.024583679, 0.022926962, 0.021275717, 0.019530845, 0.017633252, 0.015577755, 0.013403319, 0.011157076, 0.008898662, 0.006799347, 0.005005508, 0.003628616, 0.002821323, 0.00256441, 0.002505501, 0.00231945, 0.001964731, 0.001592531, 0.001594116, 0.002125339, 0.002752204, 0.003132282, 0.003071029, 0.002724739, 0.0});
        this.newRefCurve(
                "Trichinella murrelli", "TM", "Trichinella murrelli",
                new Double[]{0.133257412, 0.112363291, 0.093212398, 0.075769118, 0.072673957, 0.069619367, 0.049575492, 0.02852925, 0.012562635, 0.002452973, -0.001764908, 3.22053E-05, 0.007839231, 0.021337557, 0.040360965, 0.064806791, 0.094306222, 0.128602317, 0.167926384, 0.212236399, 0.261596696, 0.316740433, 0.379092023, 0.450362387, 0.532226082, 0.626115987, 0.733406008, 0.855710571, 0.994414579, 1.150190537, 1.32277072, 1.51064713, 1.712492224, 1.927021059, 2.15255033, 2.387044201, 2.62856327, 2.876002574, 3.140814169, 3.415209892, 3.696805985, 3.985479164, 4.281255608, 4.584353039, 4.894165597, 5.209670894, 5.530323154, 5.854709382, 6.180515921, 6.505867008, 6.828165799, 7.143707974, 7.447223587, 7.731216194, 7.987987184, 8.210258956, 8.388971726, 8.512496066, 8.569183698, 8.547946327, 8.44155415, 8.245302775, 7.95526856, 7.573687247, 7.109684078, 6.575997217, 5.990798707, 5.372845016, 4.738471807, 4.103506364, 3.480792185, 2.928527342, 2.440275573, 2.013356576, 1.658140527, 1.377501147, 1.166913422, 1.023394031, 0.940969361, 0.906261567, 0.901822518, 0.910527436, 0.91891258, 0.921586142, 0.906051671, 0.879441097, 0.847117917, 0.806201026, 0.754509252, 0.691963586, 0.619931037, 0.541741754, 0.460603576, 0.377679065, 0.294780135, 0.213722289, 0.13623343, 0.064806701, 0.01276034, -0.041582164, -0.09141837, -0.132797367, -0.165954188, -0.190839606, -0.207450734, -0.216372687, -0.218127089, -0.213666085, -0.204412708, -0.191512731, -0.176366885, -0.160046364, -0.143049128, -0.126057335, -0.109634055, -0.094115438, -0.08013116, -0.067445586, -0.056183766, -0.046381463, -0.037873482, -0.030522946, -0.024217153, -0.018861283, -0.014400965, -0.010747868, -0.007751786, -0.005210509, -0.00295996, -0.000916996, 0.000790463, 0.00202077, 0.002775811, 0.003328351, 0.003723118, 0.003532068, 0.002889651, 0.002003447, 0.001054524, 6.3915E-05, -0.000821682, -0.001662344, -0.002142382, -0.002309036, -0.002159509, -0.001607996, -0.000585678, 0.000916615, 0.002622025, 0.001398342, 0.000553421, -0.000451307, -0.001680234, -0.003173662, -0.004697325, -0.006520181, -0.008726917, -0.011418185, -0.014284066, 0.0},
                new Double[]{0.0423735, 0.039901651, 0.033075158, 0.023934514, 0.02627401, 0.0358851, 0.042061421, 0.0344144, 0.024906573, 0.015651264, 0.006813022, 0.001845538, 0.009445946, 0.016908897, 0.024123272, 0.031001546, 0.037422051, 0.043169603, 0.048139332, 0.052731266, 0.057860394, 0.064413463, 0.072790326, 0.082684435, 0.093611074, 0.105447309, 0.118392061, 0.132508393, 0.147260711, 0.161747797, 0.175051719, 0.186652878, 0.196440396, 0.204300607, 0.2101232, 0.214197432, 0.217057296, 0.219503175, 0.222441086, 0.226944451, 0.233972594, 0.244060623, 0.257179566, 0.273324848, 0.292911238, 0.315816969, 0.341239824, 0.368848253, 0.39873203, 0.430578875, 0.463223425, 0.495157105, 0.52545269, 0.553872452, 0.579964017, 0.60247896, 0.619952807, 0.632367742, 0.641118457, 0.646848848, 0.649579975, 0.649978206, 0.648772326, 0.647444062, 0.648954857, 0.655884228, 0.670088114, 0.692250332, 0.721797935, 0.759098185, 0.805476079, 0.861145014, 0.923826681, 0.989088967, 1.052819819, 1.111120486, 1.159914544, 1.195198478, 1.212961148, 1.212126241, 1.194424767, 1.162022333, 1.11805081, 1.063767148, 0.999491923, 0.926307486, 0.84491864, 0.757738976, 0.667627374, 0.576501892, 0.487395432, 0.40227583, 0.322017366, 0.248399383, 0.183249601, 0.127318127, 0.081185352, 0.046208585, 0.028632694, 0.034800825, 0.048092158, 0.059338352, 0.067211016, 0.071845604, 0.073992186, 0.074209215, 0.073058402, 0.071107361, 0.068745263, 0.066031309, 0.063138838, 0.059996218, 0.056708775, 0.053528213, 0.050576185, 0.047854268, 0.045364813, 0.043106101, 0.041123973, 0.039445639, 0.038103869, 0.037054972, 0.036180068, 0.035382201, 0.034559356, 0.03365694, 0.032681627, 0.031555098, 0.030126222, 0.028340577, 0.026297721, 0.024107704, 0.021834912, 0.01954308, 0.017262759, 0.014922125, 0.012474743, 0.01002934, 0.007767465, 0.005835132, 0.004455998, 0.00387265, 0.003840124, 0.003808749, 0.003421214, 0.002600691, 0.001437394, 0.000267711, 0.001342966, 0.002470873, 0.003210769, 0.003455526, 0.00320045, 0.002628588, 0.001936172, 0.001285342, 0.001001373, 0.001120938, 0.001322806, 0.0});
        this.newRefCurve(
                "Trichinella zimbabwensis", "TZ", "Trichinella zimbabwensis",
                new Double[]{-0.047225939, -0.041348501, -0.030124466, -0.013963957, 0.006630202, 0.030859503, 0.058096823, 0.088236548, 0.121700763, 0.159555869, 0.203362186, 0.25413639, 0.312156778, 0.377449282, 0.450125517, 0.530502097, 0.618770323, 0.71498204, 0.81578465, 0.921075348, 1.032970464, 1.151222281, 1.275554489, 1.405772265, 1.541851426, 1.684241801, 1.833450026, 1.990123984, 2.155064821, 2.328842421, 2.517497515, 2.713230311, 2.914661626, 3.121166535, 3.335683906, 3.5629472, 3.79657628, 4.03479274, 4.275970086, 4.518550187, 4.76055142, 4.999648385, 5.233518572, 5.460164673, 5.676848387, 5.880091129, 6.066207181, 6.230661762, 6.368827569, 6.476555241, 6.549408693, 6.583181277, 6.573236776, 6.513670535, 6.398974008, 6.224103966, 5.983004606, 5.668761195, 5.235634534, 4.706378502, 4.085405637, 3.373547498, 2.57167247, 1.687333804, 0.733749924, -0.275135009, -1.321657719, -2.388275881, -3.457743975, -4.508861664, -5.520781376, -6.468900124, -7.329043574, -8.084264468, -8.706661394, -9.167872664, -9.489744192, -9.668290794, -9.700575606, -9.586993921, -9.338267741, -8.974362302, -8.515973429, -7.976864931, -7.368488091, -6.701476225, -5.990932072, -5.258738638, -4.527547249, -3.818653707, -3.147002, -2.509092288, -1.928398936, -1.418942237, -0.986608455, -0.632314205, -0.353746494, -0.151585368, -0.021995559, 0.078378413, 0.136047531, 0.159707907, 0.157693893, 0.13849108, 0.109146263, 0.076660739, 0.045489803, 0.018568604, -0.002749598, -0.018476746, -0.030022736, -0.038814404, -0.043786846, -0.045656989, -0.044870451, -0.040947254, -0.036666686, -0.032506864, -0.028739185, -0.025388517, -0.022247316, -0.01921992, -0.016322054, -0.013639101, -0.011188453, -0.008899545, -0.006784092, -0.004848412, -0.003023611, -0.001296689, 0.000175744, 0.000675262, 0.0010274, 0.001258662, 0.002048596, 0.002105816, 0.001394446, 0.00049343, -0.000342873, -0.000993949, -0.001360349, -0.001389644, -0.001161889, -0.000831344, -0.000524069, -0.000227561, -6.39258E-05, 0.000665508, 0.001195136, -0.000753848, -0.002175993, -0.003598137, -0.003808577, -0.006442426, -0.00786457, -0.009286715, -0.010708859, -0.012131003, -0.013553148, 0.0},
                new Double[]{0.0256843, 0.03662919, 0.048054001, 0.058333567, 0.066067716, 0.070711173, 0.072405231, 0.072102108, 0.070902345, 0.069678779, 0.069093879, 0.069671327, 0.07216195, 0.077305635, 0.084858753, 0.093741293, 0.102973946, 0.111600099, 0.118630589, 0.124019197, 0.128330024, 0.131441633, 0.132651894, 0.131981075, 0.130319638, 0.12795267, 0.125702251, 0.124749116, 0.125350548, 0.127108522, 0.129184687, 0.130851553, 0.131845399, 0.132427394, 0.132965975, 0.134413658, 0.139000295, 0.149400878, 0.166385072, 0.187495127, 0.209472218, 0.230604403, 0.249853167, 0.265889772, 0.277071659, 0.282570178, 0.28402675, 0.283102286, 0.280328346, 0.276867263, 0.273279045, 0.269553348, 0.267077188, 0.267032292, 0.271298344, 0.282737475, 0.303622207, 0.337527268, 0.388358028, 0.455278186, 0.533138007, 0.61612665, 0.698167479, 0.774558524, 0.840382843, 0.891699998, 0.928959534, 0.956749921, 0.981846929, 1.0101893, 1.043453984, 1.07924075, 1.113500511, 1.143288294, 1.168715213, 1.189507457, 1.204534196, 1.211481619, 1.206614006, 1.190415572, 1.170376415, 1.152172058, 1.136016359, 1.115626117, 1.082626425, 1.032964424, 0.966906173, 0.889292676, 0.804978065, 0.715763192, 0.620764085, 0.520479977, 0.417390295, 0.317146712, 0.225139659, 0.145103886, 0.079192554, 0.038141839, 0.047604181, 0.072432975, 0.089651611, 0.09708126, 0.095477032, 0.086937138, 0.074447823, 0.060927083, 0.048559438, 0.03847648, 0.03042418, 0.023769169, 0.018214383, 0.013865353, 0.010953902, 0.00958874, 0.009591586, 0.010156592, 0.010752649, 0.01125998, 0.011730036, 0.012338961, 0.013155361, 0.013963421, 0.014559961, 0.014836547, 0.014733858, 0.014073368, 0.012763127, 0.011067437, 0.009308904, 0.00756659, 0.0058559, 0.004282893, 0.002900028, 0.001807828, 0.001424954, 0.001791941, 0.002270193, 0.002758064, 0.003227841, 0.003569831, 0.003754203, 0.003863645, 0.003965292, 0.003928115, 0.003406156, 0.002183999, 0.000489247, 0.001995292, 0.004229284, 0.006305791, 0.006495812, 0.005313282, 0.001838949, 0.004387932, 0.008128826, 0.011993888, 0.015892783, 0.01980554, 0.023725301, 0.0});
        this.newRefCurve(
                "Trichinella britovi", "TB", "Trichinella britovi",
                new Double[]{0.669900154, 0.648976867, 0.629796808, 0.612324362, 0.596460145, 0.581984296, 0.568604108, 0.555953656, 0.543807154, 0.532060177, 0.520777896, 0.509977736, 0.499503207, 0.488829588, 0.47744914, 0.465053015, 0.451285921, 0.435961933, 0.419457376, 0.401973241, 0.383707007, 0.364968217, 0.345913899, 0.326599286, 0.307211679, 0.305379024, 0.280587128, 0.253306653, 0.239076054, 0.213175169, 0.177790017, 0.143400134, 0.116079845, 0.093223906, 0.077381816, 0.057744775, 0.024740269, 0.008870659, -0.004349602, -0.01532416, -0.023963806, -0.029767152, -0.031348643, -0.026144066, -0.011838515, 0.013387637, 0.051892237, 0.105847922, 0.176555233, 0.265805187, 0.374705617, 0.503169671, 0.650441496, 0.814663729, 0.993732007, 1.184761771, 1.382365544, 1.580014403, 1.770809692, 1.945800822, 2.098070662, 2.221444002, 2.308053913, 2.353833615, 2.357197036, 2.317577678, 2.238752642, 2.121753488, 1.965123026, 1.772770283, 1.550923548, 1.311053084, 1.066334406, 0.826399827, 0.605122617, 0.41314944, 0.253887229, 0.131990341, 0.050659316, 0.007957109, -0.001834453, 0.009480108, 0.027556858, 0.045628737, 0.057965354, 0.061434844, 0.057338291, 0.043228112, 0.018851717, -0.013860103, -0.052549306, -0.091014918, -0.122333383, -0.143804124, -0.153531919, -0.150882229, -0.13625151, -0.104117484, -0.061068274, -0.020771944, 0.015099165, 0.058585682, 0.097302404, 0.12947511, 0.15432994, 0.171720583, 0.182568656, 0.187786087, 0.18808976, 0.184585038, 0.177634526, 0.167838901, 0.156409679, 0.143892266, 0.130554312, 0.116771296, 0.102813292, 0.089057553, 0.075832147, 0.063383266, 0.051975328, 0.041736696, 0.032757114, 0.023023646, 0.012742782, 0.003443127, -0.005022508, -0.01011579, -0.011342225, -0.012143331, -0.012669989, -0.012966148, -0.012999691, -0.012797083, -0.012515716, -0.012253455, -0.01201235, -0.011718904, -0.011318, -0.010843669, -0.010321395, -0.009754958, -0.009321697, -0.008967124, -0.008552939, -0.007975529, -0.007106385, -0.006093255, -0.00513414, -0.0069735, -0.008158165, -0.009117763, -0.009910424, -0.011013789, -0.012170084, -0.013179122, -0.013989644, -0.014610232, -0.015082451, 0.0},
                new Double[]{0.319831448, 0.313304536, 0.306782512, 0.300265694, 0.293754429, 0.287249094, 0.280750101, 0.274257901, 0.267772989, 0.261295906, 0.254827251, 0.248367681, 0.241917924, 0.235478786, 0.229051164, 0.222636054, 0.21623457, 0.20984796, 0.203477623, 0.197125137, 0.190792286, 0.184481091, 0.178193854, 0.168854681, 0.159880923, 0.138203013, 0.145291989, 0.158316647, 0.175351043, 0.179729607, 0.202583279, 0.180624496, 0.135584694, 0.086050088, 0.043660582, 0.051012988, 0.099336727, 0.153917025, 0.209039746, 0.263262597, 0.316272203, 0.368605765, 0.42138934, 0.476022824, 0.533924921, 0.595913703, 0.662389592, 0.733538737, 0.809017752, 0.88849984, 0.971503878, 1.056945024, 1.142947274, 1.228039939, 1.310828408, 1.389747177, 1.463482569, 1.531692832, 1.593383468, 1.646973821, 1.691399852, 1.727538191, 1.755396545, 1.774095332, 1.781724034, 1.777322623, 1.762245352, 1.737701436, 1.705043306, 1.670395995, 1.642514713, 1.627774708, 1.629564841, 1.647955679, 1.679896083, 1.719385377, 1.759213599, 1.791038955, 1.806421044, 1.798653196, 1.763819855, 1.698521027, 1.601855383, 1.478521108, 1.333927526, 1.174744, 1.010776682, 0.851284786, 0.70313262, 0.570859276, 0.455519197, 0.356923982, 0.274909254, 0.207718015, 0.153628687, 0.110830065, 0.077159776, 0.050587108, 0.031311114, 0.026392868, 0.038382412, 0.056220911, 0.07488954, 0.092697577, 0.108557066, 0.121798614, 0.132092224, 0.139282804, 0.143601435, 0.145373781, 0.144930994, 0.142537288, 0.138363085, 0.13262362, 0.12566717, 0.117682309, 0.108772682, 0.099111652, 0.088913417, 0.078369733, 0.067688148, 0.057176639, 0.047342387, 0.03887903, 0.032531308, 0.028910623, 0.0280537, 0.029134848, 0.03106641, 0.033086084, 0.034853847, 0.036330678, 0.037543247, 0.038382176, 0.038702529, 0.038470585, 0.037662518, 0.036243967, 0.034255205, 0.031860543, 0.029228107, 0.026406507, 0.023399778, 0.020280427, 0.017170701, 0.014227006, 0.011564462, 0.009373972, 0.007878807, 0.007151924, 0.007114439, 0.007683892, 0.008709924, 0.010032719, 0.011491174, 0.012880216, 0.014040931, 0.014827231, 0.015125974, 0.0}
        );
        this.newRefCurve(
                "Trichinella patagoniensis", "TP", "Trichinella patagoniensis",
                new Double[]{-0.066279315, -0.069919228, -0.072331654, -0.074320302, -0.072980227, -0.065384375, -0.054680997, -0.042662413, -0.029986693, -0.01726134, -0.004926851, 0.006648366, 0.014845897, 0.019318146, 0.022249091, 0.022723549, 0.020830932, 0.017764999, 0.014709854, 0.011706531, 0.009427817, 0.008648784, 0.009855169, 0.013319338, 0.019315201, 0.028071058, 0.039547612, 0.05297435, 0.06240661, 0.074423258, 0.088648252, 0.103950536, 0.119180693, 0.132913891, 0.143656594, 0.150119711, 0.1511555, 0.145807201, 0.133548565, 0.114195849, 0.095843469, 0.070962628, 0.040862914, 0.013016866, -0.01786117, -0.052628955, -0.092116503, -0.137764581, -0.191327024, -0.253995531, -0.327322195, -0.413081696, -0.512972365, -0.629067932, -0.762558523, -0.913727882, -1.082989835, -1.271372566, -1.478096218, -1.701035988, -1.963413389, -2.245792789, -2.587585728, -2.928565642, -3.252599047, -3.544505166, -3.788036266, -3.970616356, -4.084029619, -4.12232159, -4.085444543, -3.974913372, -3.796142459, -3.561426506, -3.2811663, -2.967919579, -2.638540947, -2.304718149, -1.974792415, -1.65968191, -1.356103322, -1.079827607, -0.849867819, -0.66668072, -0.527067221, -0.42404942, -0.346075024, -0.286753322, -0.242648297, -0.210559115, -0.186614905, -0.165362129, -0.143119458, -0.123665556, -0.103512109, -0.079524166, -0.05588709, -0.034479567, -0.017753888, -0.007718209, -0.000168264, 0.002823455, 0.007693636, 0.009241434, 0.008109239, 0.005169696, 0.001747487, -0.002362294, -0.005689334, -0.007634356, -0.008619854, -0.009144325, -0.009026567, -0.008371602, -0.007438922, -0.006425798, -0.005651229, -0.00528825, -0.005021241, -0.004830266, -0.004513401, -0.004005042, -0.003716323, -0.003387191, -0.001460231, 0.000766572, 0.00214744, 0.003068932, 0.003399062, 0.00364137, 0.004112818, 0.004619724, 0.002610809, 0.002066154, 0.000227252, -0.001230061, -0.002692976, -0.003928341, -0.005176394, -0.004077092, -0.004025997, -0.004545275, -0.004883227, -0.005148452, -0.005396726, -0.005586341, -0.005620794, -0.005544235, -0.005516301, -0.008510314, -0.011098279, -0.013686245, -0.01627421, -0.018862175, -0.021450141, -0.024038106, -0.026626072, -0.029214037, -0.031802002, 0.0},
                new Double[]{0.059416771, 0.061428569, 0.064646241, 0.069233255, 0.075075762, 0.081809003, 0.088956735, 0.096053697, 0.102824792, 0.10908547, 0.114638005, 0.119409955, 0.123377424, 0.126559777, 0.129080775, 0.131074561, 0.132567191, 0.133476445, 0.133711104, 0.13325621, 0.132153876, 0.130529454, 0.128519471, 0.126498712, 0.125072273, 0.124863131, 0.126319733, 0.129837294, 0.135591894, 0.143447184, 0.15307377, 0.164113241, 0.176321295, 0.189484406, 0.203476076, 0.218214661, 0.233586293, 0.249417981, 0.265636166, 0.282290435, 0.299554783, 0.31760517, 0.336470695, 0.356002518, 0.376073337, 0.396517587, 0.417095466, 0.437426658, 0.456952166, 0.475148085, 0.491458729, 0.505229541, 0.516108943, 0.524088813, 0.529362445, 0.532443099, 0.533980228, 0.534594758, 0.535033508, 0.536285713, 0.539758185, 0.547284591, 0.560619272, 0.581643546, 0.611720068, 0.650655893, 0.696851295, 0.74754859, 0.799146121, 0.846769741, 0.885268876, 0.910406919, 0.918854549, 0.909084404, 0.881261304, 0.836481024, 0.776738686, 0.704589096, 0.622664721, 0.53437748, 0.443963487, 0.355933127, 0.275274215, 0.206336631, 0.152406175, 0.116266659, 0.097222716, 0.088805958, 0.083242529, 0.07640301, 0.067979378, 0.0594246, 0.051712811, 0.045027236, 0.038738766, 0.032376794, 0.025989448, 0.019874495, 0.014814239, 0.012396844, 0.013764791, 0.017421695, 0.021515051, 0.025192445, 0.02814976, 0.030265084, 0.031465328, 0.031846811, 0.031496036, 0.030559713, 0.029077459, 0.027203426, 0.025008454, 0.022614048, 0.020157464, 0.017756392, 0.015473721, 0.013301572, 0.011228037, 0.009265175, 0.007439804, 0.005769412, 0.004301581, 0.003162043, 0.002594668, 0.002878123, 0.003881245, 0.005286418, 0.006949509, 0.008814676, 0.006717586, 0.005750216, 0.003244679, 0.005317034, 0.009002231, 0.012592086, 0.016377761, 0.02023673, 0.024098978, 0.028387244, 0.032775181, 0.037221742, 0.04171256, 0.046234741, 0.050779906, 0.055342394, 0.059918246, 0.06450462, 0.069099419, 0.073701068, 0.07830836, 0.082920354, 0.087536306, 0.092155623, 0.096777821, 0.101402508, 0.106029357, 0.110658098, 0.115288502, 0.0}
        );
        this.newRefCurve(
                "Trichinella nelsoni", "Tne", "Trichinella nelsoni",
                new Double[]{0.032954093, 0.031148699, 0.031086534, 0.032731981, 0.035985658, 0.040627703, 0.046365408, 0.050847694, 0.058036689, 0.064517105, 0.072588502, 0.082167692, 0.0910267, 0.09906628, 0.104068115, 0.108509851, 0.112740217, 0.119915868, 0.127281738, 0.134164096, 0.13982782, 0.146377908, 0.153823311, 0.161977951, 0.173322014, 0.188134223, 0.201809379, 0.215288234, 0.232172062, 0.253242352, 0.278984101, 0.309223914, 0.344081529, 0.383538826, 0.427294933, 0.476296856, 0.527811558, 0.58108791, 0.635917826, 0.692291749, 0.750337078, 0.809050687, 0.86880331, 0.934108966, 1.006769173, 1.088278285, 1.180762545, 1.286011589, 1.410979008, 1.557638938, 1.724426561, 1.913045788, 2.125309232, 2.362924187, 2.62876623, 2.926295618, 3.256861171, 3.618522708, 4.008232832, 4.420803909, 4.851355601, 5.293229661, 5.734595685, 6.152008286, 6.54742454, 6.910599704, 7.23303646, 7.50450373, 7.713031412, 7.851711191, 7.915444648, 7.905741601, 7.827394398, 7.681275125, 7.473078332, 7.209528315, 6.894036676, 6.535576418, 6.146629667, 5.735046569, 5.305946548, 4.855103283, 4.381724903, 3.903605062, 3.428382838, 2.963419398, 2.518534484, 2.099549853, 1.71159358, 1.360529103, 1.050151057, 0.784167287, 0.564701533, 0.388740524, 0.251869083, 0.148669724, 0.072821776, 0.019159458, -0.017230519, -0.03596621, -0.053441983, -0.06690738, -0.074196685, -0.07944598, -0.0831153, -0.085647041, -0.086816658, -0.086429521, -0.084839314, -0.082132897, -0.078770031, -0.075138473, -0.069680169, -0.064082077, -0.058725355, -0.053708036, -0.049197211, -0.045240624, -0.041801101, -0.040231704, -0.039495452, -0.038311802, -0.036741831, -0.034901893, -0.032855506, -0.030569058, -0.028006917, -0.025135295, -0.022000083, -0.018700528, -0.015488956, -0.012638092, -0.010290681, -0.008458543, -0.007130375, -0.006257539, -0.005741205, -0.005357529, -0.004889423, -0.004341772, -0.00376439, -0.003182681, -0.002617507, -0.002120423, -0.001672373, -0.001114218, -0.000275357, 0.000812481, 0.001992214, 0.000190593, -0.001175568, -0.002386161, -0.003398205, -0.004947687, -0.006779786, -0.00886098, -0.011080063, -0.013242888, -0.015388306, 0.0},
                new Double[]{0.028438228, 0.024897454, 0.021393428, 0.017947685, 0.014601546, 0.011442729, 0.00867825, 0.006806845, 0.006480959, 0.006108499, 0.010777835, 0.01489754, 0.015118004, 0.013893807, 0.012152306, 0.010484551, 0.009663695, 0.010172305, 0.01186665, 0.014116248, 0.016097973, 0.017133597, 0.016798534, 0.015141043, 0.013208588, 0.013363065, 0.017095398, 0.023109315, 0.029879265, 0.036589543, 0.042519861, 0.047050793, 0.049937612, 0.051283346, 0.051593463, 0.051500079, 0.051296125, 0.05109302, 0.051023981, 0.051170947, 0.051468671, 0.051808205, 0.052296819, 0.053166316, 0.054647619, 0.057268443, 0.061408814, 0.06711357, 0.074734847, 0.085446588, 0.100645666, 0.121133574, 0.147749924, 0.182032882, 0.2254425, 0.278184015, 0.338792865, 0.40499146, 0.475546238, 0.55019376, 0.628036467, 0.705951375, 0.778952527, 0.84426057, 0.901512553, 0.949090372, 0.985164287, 1.009588286, 1.022370753, 1.02576432, 1.02352325, 1.01753896, 1.008757591, 0.995791112, 0.977043494, 0.954670267, 0.931791995, 0.908992737, 0.88587548, 0.86122489, 0.834550028, 0.805578684, 0.77187869, 0.731519933, 0.684411628, 0.631712108, 0.576461591, 0.521371536, 0.46783469, 0.416561444, 0.367066594, 0.319060658, 0.272835865, 0.228141974, 0.18531789, 0.144929684, 0.106664046, 0.07065124, 0.038301824, 0.014704956, 0.022722231, 0.040711523, 0.055457545, 0.066200433, 0.073111092, 0.07669172, 0.07749489, 0.07587604, 0.072355573, 0.067475001, 0.061734284, 0.055672518, 0.049696494, 0.043996357, 0.038757117, 0.03410795, 0.030172492, 0.02703321, 0.024623597, 0.022738627, 0.021190546, 0.01984191, 0.018579772, 0.017296312, 0.015925839, 0.014451127, 0.012898824, 0.011362854, 0.009946063, 0.008739281, 0.007816898, 0.007192378, 0.006890296, 0.006958988, 0.007391338, 0.00810909, 0.008951788, 0.009743033, 0.010343366, 0.010681579, 0.010716574, 0.010465085, 0.009965266, 0.009268745, 0.008428903, 0.007460501, 0.006370444, 0.00518514, 0.003989814, 0.002814835, 0.001611024, 0.000598777, 0.001120112, 0.001932287, 0.00236519, 0.002388909, 0.002146915, 0.001807539, 0.001458516, 0.0}
        );
        this.newRefCurve(
                "Trichinella papuae", "Tpa", "Trichinella papuae",
                new Double[]{-0.037433468, -0.030357821, -0.018686703, -0.002768539, 0.018166202, 0.043090643, 0.071770386, 0.103277142, 0.138811941, 0.179349811, 0.225768699, 0.278632524, 0.338335247, 0.405094953, 0.478993684, 0.560139684, 0.648185958, 0.742695242, 0.843808773, 0.951696124, 1.066430943, 1.188071909, 1.316494678, 1.451003247, 1.591326024, 1.737544028, 1.889901129, 2.048894483, 2.215063586, 2.38847897, 2.570649637, 2.760946317, 2.958707199, 3.16333609, 3.374089688, 3.590567589, 3.812324418, 4.038634167, 4.268585902, 4.501059471, 4.734499834, 4.967220015, 5.197488384, 5.42322234, 5.641371653, 5.848709612, 6.042171371, 6.21792779, 6.371918653, 6.500190424, 6.5982824, 6.662212962, 6.688252493, 6.672205318, 6.610183617, 6.498099383, 6.331140467, 6.104566326, 5.814937528, 5.45938756, 5.037769117, 4.551507682, 4.002517134, 3.397350074, 2.746216763, 2.060197272, 1.346409157, 0.621018088, -0.09648267, -0.791007927, -1.449428649, -2.058088829, -2.6047796, -3.081736053, -3.48145722, -3.799899032, -4.03737718, -4.194151649, -4.270974315, -4.271119985, -4.199246213, -4.060749362, -3.866873887, -3.627993399, -3.344625672, -3.026039724, -2.682037464, -2.325336019, -1.967270442, -1.618163147, -1.287963204, -0.984997591, -0.71670136, -0.489097966, -0.307825091, -0.171082718, -0.075032161, -0.014722061, 0.013432547, 0.019370664, 0.009369426, -0.017054209, -0.050860065, -0.084860221, -0.11514516, -0.139340534, -0.156321751, -0.166162895, -0.169590565, -0.16746837, -0.160967285, -0.151143207, -0.138855897, -0.12513548, -0.110896626, -0.096896925, -0.083815735, -0.072021192, -0.061726836, -0.052949953, -0.045417134, -0.038864803, -0.033117184, -0.02802092, -0.023354146, -0.018939252, -0.01477663, -0.011025962, -0.00782368, -0.004749778, -0.002028376, 0.000552534, 0.002725389, 0.003658568, 0.003720012, 0.00340243, 0.002986503, 0.002320484, 0.001629106, 0.000962823, 0.000367235, -9.53999E-05, -0.000400138, -0.000798816, -0.001206683, -0.000810902, -0.000798919, -0.000859785, -0.001221396, -0.002181629, -0.002037812, -0.002645836, -0.001777724, -0.000909613, -4.15016E-05, 0.00082661, 0.001694721, 0.002562832, 0.003430944, 0.0},
                new Double[]{0.008570572, 0.00878856, 0.008691252, 0.008431507, 0.008110571, 0.007899399, 0.007931942, 0.008536813, 0.009921003, 0.011727058, 0.013398114, 0.014862541, 0.016429331, 0.018438176, 0.021046203, 0.024141063, 0.027703551, 0.031969825, 0.037277628, 0.043730522, 0.051060665, 0.059067925, 0.067729595, 0.076783242, 0.086072638, 0.095551338, 0.105129826, 0.114763698, 0.124405085, 0.134058, 0.144108414, 0.15479232, 0.166042872, 0.177810471, 0.190075783, 0.202817301, 0.215916648, 0.229234239, 0.242794705, 0.256846848, 0.271581375, 0.286995937, 0.302544397, 0.317990592, 0.3339153, 0.35074947, 0.368558655, 0.387363254, 0.407025112, 0.427439953, 0.4489032, 0.471594209, 0.495589716, 0.520655909, 0.546026571, 0.571279943, 0.596423036, 0.621213109, 0.644972361, 0.666742931, 0.685649351, 0.701148009, 0.712365198, 0.718289402, 0.7186251, 0.713501647, 0.703679684, 0.690220533, 0.67385915, 0.65556938, 0.635528155, 0.613988585, 0.591818655, 0.568691853, 0.544975022, 0.521131392, 0.495893202, 0.469061692, 0.441444827, 0.413046366, 0.383959977, 0.354915659, 0.325856164, 0.297648795, 0.270547939, 0.244275631, 0.219528914, 0.197202587, 0.177695292, 0.160669408, 0.144918045, 0.1285053, 0.111127366, 0.094039272, 0.077367306, 0.061647029, 0.046690936, 0.032509044, 0.020895541, 0.015078853, 0.016246106, 0.020041747, 0.023312358, 0.025631005, 0.02709873, 0.027805514, 0.028137473, 0.028015848, 0.027439186, 0.02655411, 0.025301143, 0.024007088, 0.022917041, 0.022013453, 0.021363403, 0.020979476, 0.020708001, 0.020309402, 0.019534259, 0.018248938, 0.016544022, 0.014601812, 0.012591351, 0.010605462, 0.008710174, 0.007009975, 0.005609281, 0.004490311, 0.003555825, 0.002753267, 0.002129257, 0.001798958, 0.001768209, 0.001937608, 0.002359407, 0.00304575, 0.003760224, 0.004260686, 0.004433444, 0.004257982, 0.00377828, 0.003067695, 0.002182128, 0.001244396, 0.001010123, 0.002095089, 0.003493573, 0.004879486, 0.006451995, 0.004695943, 0.002851046, 0.003453182, 0.005759092, 0.008476822, 0.011313379, 0.01419772, 0.017105691, 0.020027, 0.022956556, 0.0}
        );

    }

    private void newRefCurve(String name, String acronym, String note, Double[] values, Double[] marginValues) {
        RefCurve curve = new RefCurve();
        curve.setName(name);
        curve.setAcronym(acronym);
        curve.setNote(note);
        curve.setValues(Arrays.asList(values));
        if (marginValues != null) {
            ErrorMargin margin = new ErrorMargin();
            margin.setValues(Arrays.asList(marginValues));
            curve.setErrorMargin(margin);
        }
        refCurveService.create(curve);
    }

}