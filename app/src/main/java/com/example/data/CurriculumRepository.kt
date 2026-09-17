package com.example.data

import com.example.model.Board
import com.example.model.Chapter
import com.example.model.ChapterFullContent
import com.example.model.ConceptPoint
import com.example.model.GradeClass
import com.example.model.KeyFormula
import com.example.model.QuizQuestion
import com.example.model.StepLearningItem
import com.example.model.Stream
import com.example.model.Subject

object CurriculumRepository {

    // Available Subjects
    val subjects: List<Subject> = listOf(
        // Class 10 Subjects
        Subject(
            id = "c10_math",
            name = "Mathematics",
            grade = GradeClass.CLASS_10,
            stream = Stream.GENERAL,
            description = "Master real numbers, algebra, trigonometry, geometry, and statistics.",
            colorHex = 0xFF2563EB,
            iconName = "calculate",
            totalChapters = 6
        ),
        Subject(
            id = "c10_sci",
            name = "Science",
            grade = GradeClass.CLASS_10,
            stream = Stream.GENERAL,
            description = "Comprehensive physics, chemistry, and biology foundations.",
            colorHex = 0xFF0D9488,
            iconName = "science",
            totalChapters = 5
        ),
        Subject(
            id = "c10_eng",
            name = "English",
            grade = GradeClass.CLASS_10,
            stream = Stream.GENERAL,
            description = "Prose, poetry analysis, communicative writing, and grammar.",
            colorHex = 0xFF7C3AED,
            iconName = "menu_book",
            totalChapters = 3
        ),
        Subject(
            id = "c10_soc",
            name = "Social Science",
            grade = GradeClass.CLASS_10,
            stream = Stream.GENERAL,
            description = "History, geography, democratic politics, and economic development.",
            colorHex = 0xFFEA580C,
            iconName = "public",
            totalChapters = 3
        ),
        Subject(
            id = "c10_it",
            name = "Computer / IT",
            grade = GradeClass.CLASS_10,
            stream = Stream.GENERAL,
            description = "Digital literacy, cyber ethics, algorithms, and intro to Python.",
            colorHex = 0xFF0284C7,
            iconName = "terminal",
            totalChapters = 3
        ),

        // Class 12 Subjects (Science & Tech)
        Subject(
            id = "c12_phy",
            name = "Physics",
            grade = GradeClass.CLASS_12,
            stream = Stream.SCIENCE,
            description = "Electrostatics, current electricity, magnetism, optics, and modern physics.",
            colorHex = 0xFF3B82F6,
            iconName = "bolt",
            totalChapters = 4
        ),
        Subject(
            id = "c12_chem",
            name = "Chemistry",
            grade = GradeClass.CLASS_12,
            stream = Stream.SCIENCE,
            description = "Physical solutions, electrochemistry, kinetics, organic mechanisms.",
            colorHex = 0xFF10B981,
            iconName = "biotech",
            totalChapters = 4
        ),
        Subject(
            id = "c12_math",
            name = "Mathematics",
            grade = GradeClass.CLASS_12,
            stream = Stream.SCIENCE,
            description = "Calculus, matrices, determinants, vectors, and 3D geometry.",
            colorHex = 0xFF6366F1,
            iconName = "functions",
            totalChapters = 4
        ),
        Subject(
            id = "c12_bio",
            name = "Biology",
            grade = GradeClass.CLASS_12,
            stream = Stream.SCIENCE,
            description = "Genetics, evolution, biotechnology, ecology, and human physiology.",
            colorHex = 0xFF059669,
            iconName = "psychology",
            totalChapters = 3
        ),
        Subject(
            id = "c12_cs",
            name = "Computer Science",
            grade = GradeClass.CLASS_12,
            stream = Stream.SCIENCE,
            description = "Advanced Python, OOP, SQL databases, computer networking.",
            colorHex = 0xFF0891B2,
            iconName = "code",
            totalChapters = 3
        ),
        Subject(
            id = "c12_eng",
            name = "English Core",
            grade = GradeClass.CLASS_12,
            stream = Stream.GENERAL,
            description = "Flamingo literature, Vistas supplementary, and formal communication.",
            colorHex = 0xFF8B5CF6,
            iconName = "auto_stories",
            totalChapters = 3
        )
    )

    // Chapters Data
    val chapters: List<Chapter> = listOf(
        // Class 10 Math
        Chapter("c10_m_ch1", "c10_math", 1, "Real Numbers", "Euclid division lemma, Fundamental Theorem of Arithmetic, irrationality proofs.", 45, listOf("Understand prime factorization", "Prove √2, √3 irrationality", "Compute HCF & LCM using prime factors"), true),
        Chapter("c10_m_ch2", "c10_math", 2, "Polynomials", "Zeroes of quadratic and cubic polynomials and their algebraic relationships.", 40, listOf("Find zeroes of quadratic polynomials", "Verify coefficient relations", "Interpret geometric graphs")),
        Chapter("c10_m_ch3", "c10_math", 3, "Quadratic Equations", "Roots by factorization, completing square, and the quadratic formula.", 50, listOf("Solve equations ax² + bx + c = 0", "Discriminant nature of roots", "Real-world word problem modeling"), true),
        Chapter("c10_m_ch4", "c10_math", 4, "Triangles & Similarity", "Basic proportionality theorem, criteria for similarity of triangles.", 55, listOf("Apply Thales Theorem (BPT)", "Prove AAA, SSS, SAS similarity", "Solve geometric area ratios")),
        Chapter("c10_m_ch5", "c10_math", 5, "Introduction to Trigonometry", "Trigonometric ratios, standard angles (0°, 30°, 45°, 60°, 90°), and identities.", 60, listOf("Master sin, cos, tan, cot, sec, cosec", "Memorize standard values table", "Prove fundamental trigonometric identities"), true),
        Chapter("c10_m_ch6", "c10_math", 6, "Statistics & Probability", "Mean, median, mode of grouped data and classical probability concepts.", 40, listOf("Compute direct & assumed mean", "Calculate median using ogive curves", "Solve independent event probabilities")),

        // Class 10 Science
        Chapter("c10_s_ch1", "c10_sci", 1, "Chemical Reactions & Equations", "Balancing chemical equations, combination, decomposition, displacement, and redox.", 50, listOf("Balance mass in reactions", "Distinguish endothermic vs exothermic", "Identify corrosion & rancidity"), true),
        Chapter("c10_s_ch2", "c10_sci", 2, "Acids, Bases & Salts", "pH scale, neutralization, chlor-alkali process, bleaching powder, and plaster of Paris.", 45, listOf("Understand indicators (litmus, phenolphthalein)", "Analyze pH values in daily life", "Prepare common salts and their uses")),
        Chapter("c10_s_ch3", "c10_sci", 3, "Life Processes", "Nutrition, aerobic and anaerobic respiration, transportation, and excretion in organisms.", 60, listOf("Draw and explain human digestive system", "Trace xylem and phloem transport", "Understand nephron filtration in kidneys"), true),
        Chapter("c10_s_ch4", "c10_sci", 4, "Light: Reflection & Refraction", "Spherical mirrors, lens formula, magnification, and Snell's law of refraction.", 55, listOf("Construct ray diagrams for concave/convex mirrors", "Apply lens formula: 1/f = 1/v - 1/u", "Calculate optical power of lenses"), true),
        Chapter("c10_s_ch5", "c10_sci", 5, "Electricity", "Ohm's law, resistance, resistivity, series & parallel circuits, Joule heating effect.", 50, listOf("Verify V = IR experimentally", "Calculate equivalent resistances", "Compute electrical energy and cost"), true),

        // Class 10 English & Social Science
        Chapter("c10_e_ch1", "c10_eng", 1, "A Letter to God", "Story of Lencho's unshakeable faith in God and the unexpected irony of human charity.", 35, listOf("Analyze character sketches", "Identify irony in literature", "Compose formal letters of inquiry")),
        Chapter("c10_ss_ch1", "c10_soc", 1, "The Rise of Nationalism in Europe", "French Revolution, Napoleonic Code, unification of Italy and Germany.", 50, listOf("Trace Liberal Nationalism", "Examine Treaty of Vienna 1815", "Analyze Bismarck and Garibaldi roles"), true),
        Chapter("c10_it_ch1", "c10_it", 1, "Cyber Ethics & Digital Safety", "Netiquette, intellectual property, cyberbullying, phishing, and password hygiene.", 30, listOf("Practice ethical online safety", "Identify malicious cyber scams", "Understand digital footprint")),

        // Class 12 Physics
        Chapter("c12_p_ch1", "c12_phy", 1, "Electric Charges & Fields", "Coulomb's law, electric field lines, electric dipole, and Gauss's Law applications.", 60, listOf("Calculate Coulomb forces between charges", "Determine electric field on dipole axis", "Apply Gauss's law for symmetrical charge distributions"), true),
        Chapter("c12_p_ch2", "c12_phy", 2, "Electrostatic Potential & Capacitance", "Equipotential surfaces, potential energy of dipole, parallel plate capacitors, dielectric effect.", 55, listOf("Derive potential V = kq/r", "Calculate capacitance C = ε₀A/d", "Solve combinations of capacitors in circuits")),
        Chapter("c12_p_ch3", "c12_phy", 3, "Current Electricity", "Drift velocity, Ohm's law vector form, Kirchhoff's rules, Wheatstone bridge.", 60, listOf("Derive relation between current & drift speed", "Apply Kirchhoff's Current & Voltage laws", "Analyze balanced Wheatstone bridge circuits"), true),
        Chapter("c12_p_ch4", "c12_phy", 4, "Ray & Wave Optics", "Huygens wave principle, interference, Young's double-slit experiment, diffraction.", 65, listOf("Construct wavefronts using Huygens principle", "Derive fringe width β = λD/d in YDSE", "Explain single-slit diffraction pattern")),

        // Class 12 Chemistry
        Chapter("c12_c_ch1", "c12_chem", 1, "Solutions", "Raoult's law, colligative properties, elevation of boiling point, depression of freezing point, van 't Hoff factor.", 55, listOf("Calculate molarity, molality, mole fraction", "Apply Raoult's law for volatile solutes", "Determine molecular mass using osmotic pressure"), true),
        Chapter("c12_c_ch2", "c12_chem", 2, "Electrochemistry", "Galvanic cells, Nernst equation, Kohlrausch's law, electrolysis, and fuel cells.", 60, listOf("Calculate cell EMF using Nernst equation", "Apply Kohlrausch's law at infinite dilution", "Relate Gibbs free energy ΔG to cell potential E°"), true),
        Chapter("c12_c_ch3", "c12_chem", 3, "Chemical Kinetics", "Rate of reaction, rate law, integrated rate equations for zero and first order, Arrhenius equation.", 50, listOf("Determine order and molecularity", "Derive half-life t₁/₂ for 1st order reactions", "Calculate activation energy Ea using Arrhenius plot")),
        Chapter("c12_c_ch4", "c12_chem", 4, "Coordination Compounds", "Werner's theory, IUPAC nomenclature, isomerism, valence bond theory, and crystal field splitting.", 55, listOf("Name coordination complexes according to IUPAC", "Distinguish tetrahedral vs octahedral splitting", "Predict magnetic moments using VBT")),

        // Class 12 Math
        Chapter("c12_m_ch1", "c12_math", 1, "Relations & Functions", "Reflexive, symmetric, transitive relations, equivalence relations, injective and surjective mappings.", 45, listOf("Check reflexivity, symmetry, and transitivity", "Prove bijective nature of functions", "Compose functions and identify inverses")),
        Chapter("c12_m_ch2", "c12_math", 2, "Matrices & Determinants", "Matrix operations, minors, cofactors, inverse by adjoint, and solving linear systems by Cramer's/matrix method.", 50, listOf("Perform matrix multiplication and transposes", "Calculate inverse A⁻¹ = adj(A)/|A|", "Solve system of linear equations AX = B"), true),
        Chapter("c12_m_ch3", "c12_math", 3, "Continuity & Differentiability", "Continuity at a point, chain rule, derivatives of implicit and parametric functions, logarithmic differentiation.", 65, listOf("Evaluate limits for continuity", "Differentiate y = u(x)^v(x) using logs", "Apply Rolle's and Mean Value Theorems"), true),
        Chapter("c12_m_ch4", "c12_math", 4, "Integrals & Applications", "Definite & indefinite integrals, substitution, by parts, partial fractions, and area under simple curves.", 70, listOf("Integrate by parts: ∫u v dx = u∫v - ∫(u'∫v)", "Evaluate definite integrals using properties", "Calculate area bounded between parabola and lines")),

        // Class 12 Computer Science
        Chapter("c12_cs_ch1", "c12_cs", 1, "Python Data Structures", "Lists, dictionaries, tuples, stacks implementation using lists, time complexities.", 45, listOf("Implement push and pop operations on stack", "Use dictionary comprehensions", "Analyze linear and binary search algorithms")),
        Chapter("c12_cs_ch2", "c12_cs", 2, "Object-Oriented Programming in Python", "Classes, objects, constructors (__init__), inheritance, encapsulation, polymorphism.", 50, listOf("Create structured classes with instance variables", "Implement single and multilevel inheritance", "Demonstrate method overriding")),
        Chapter("c12_cs_ch3", "c12_cs", 3, "Relational Databases & SQL", "DDL and DML commands, joins, aggregate functions (COUNT, SUM, AVG, GROUP BY, HAVING).", 55, listOf("Write complex SQL queries with multiple conditions", "Perform INNER, LEFT, and RIGHT JOINs", "Design normalized database tables with primary & foreign keys"), true)
    )

    fun getSubjectsForGrade(grade: GradeClass, stream: Stream? = null): List<Subject> {
        return subjects.filter { subject ->
            subject.grade == grade && (stream == null || subject.stream == Stream.GENERAL || subject.stream == stream)
        }
    }

    fun getChaptersForSubject(subjectId: String): List<Chapter> {
        return chapters.filter { it.subjectId == subjectId }
    }

    fun getChapterById(chapterId: String): Chapter? {
        return chapters.find { it.id == chapterId }
    }

    fun getSubjectById(subjectId: String): Subject? {
        return subjects.find { it.id == subjectId }
    }

    // Full educational content for each chapter
    fun getChapterFullContent(chapterId: String): ChapterFullContent {
        val chapter = getChapterById(chapterId) ?: chapters.first()

        val sampleNotes = when (chapterId) {
            "c10_m_ch1" -> listOf(
                "1. Fundamental Theorem of Arithmetic: Every composite number can be expressed (factorised) as a product of primes, and this factorisation is unique, apart from the order in which the prime factors occur.",
                "2. Finding HCF and LCM: For any two positive integers a and b, HCF(a, b) × LCM(a, b) = a × b. Note that this product formula holds strictly for two numbers, not for three.",
                "3. Proof of Irrationality: To prove √p is irrational (where p is prime), assume √p = a/b in simplest form with a, b co-prime integers. Squaring yields p = a²/b², so a² = p·b², meaning p divides a². By Euclid's theorem, p divides a. Writing a = p·k leads to b² = p·k², meaning p divides b. This contradicts that a and b are co-prime.",
                "4. Decimal Expansion: A rational number p/q in lowest terms has a terminating decimal expansion if and only if the prime factorisation of q is of the form 2ⁿ · 5ᵐ, where n and m are non-negative integers."
            )
            "c10_s_ch1" -> listOf(
                "1. Chemical Reactions: A chemical reaction involves the breaking and making of bonds between atoms to produce new substances with entirely distinct chemical properties.",
                "2. Law of Conservation of Mass: Mass can neither be created nor destroyed in a chemical reaction. Therefore, the total mass of reactants must equal the total mass of products, requiring balanced chemical equations.",
                "3. Types of Reactions:\n• Combination: Two or more reactants combine to form a single product (CaO + H₂O → Ca(OH)₂ + Heat).\n• Decomposition: A single reactant breaks down into simpler products (2FeSO₄ → Fe₂O₃ + SO₂ + SO₃).\n• Displacement: A more reactive element displaces a less reactive element (Fe + CuSO₄ → FeSO₄ + Cu).\n• Double Displacement: Mutual exchange of ions between two compounds (Na₂SO₄ + BaCl₂ → BaSO₄↓ + 2NaCl).",
                "4. Oxidation & Reduction (Redox): Oxidation is the gain of oxygen or loss of hydrogen (or loss of electrons). Reduction is the loss of oxygen or gain of hydrogen (or gain of electrons). Corrosion and rancidity are everyday oxidation effects."
            )
            "c12_p_ch1" -> listOf(
                "1. Electric Charge: Fundamental intrinsic property of matter. Quantized as Q = ±ne, where e = 1.602 × 10⁻¹⁹ C. Electric charge is strictly conserved in isolated systems.",
                "2. Coulomb's Law: The electrostatic force between two stationary point charges q₁ and q₂ is directly proportional to the product of their charges and inversely proportional to the square of the separation distance: F = (1 / 4πε₀) · (|q₁ · q₂| / r²). Here ε₀ = 8.854 × 10⁻¹² C² N⁻¹ m⁻².",
                "3. Electric Dipole: A pair of equal and opposite charges ±q separated by distance 2a. Dipole moment p = q · 2a directed from negative to positive charge. Torque in uniform electric field: τ = p × E.",
                "4. Gauss's Law: The total electric flux Φ through any closed Gaussian surface in vacuum is equal to 1/ε₀ times the net charge enclosed: ∮ E · dA = q_enclosed / ε₀."
            )
            "c12_c_ch1" -> listOf(
                "1. Types of Solutions & Concentration Terms: Molarity (M = moles of solute / liters of solution), Molality (m = moles of solute / kg of solvent), and Mole Fraction (χ = n_A / (n_A + n_B)). Molality is temperature independent.",
                "2. Raoult's Law: For a solution of volatile liquids, the partial vapour pressure of each component is directly proportional to its mole fraction: P_A = P°_A · χ_A. Ideal solutions obey Raoult's law across all concentrations with ΔH_mix = 0 and ΔV_mix = 0.",
                "3. Colligative Properties: Properties that depend solely on the number of solute particles, not on their nature:\n• Relative lowering of vapour pressure: (P°₁ - P₁) / P°₁ = χ₂\n• Elevation of boiling point: ΔT_b = K_b · m\n• Depression of freezing point: ΔT_f = K_f · m\n• Osmotic pressure: Π = CRT",
                "4. Van 't Hoff Factor (i): Ratio of normal molar mass to abnormal observed molar mass. For association i < 1, for dissociation i > 1."
            )
            else -> listOf(
                "1. Comprehensive Chapter Synopsis: This chapter introduces fundamental scientific and mathematical frameworks essential for board examinations and competitive entrance tests.",
                "2. Key Principles & Derivations: Focus on foundational axioms, standard textbook proofs, and systematic problem solving methodologies.",
                "3. Examination Strategies: Emphasize step-by-step presentation, proper labeling of diagrams, explicit unit notation, and verification of intermediate algebraic steps."
            )
        }

        val sampleFormulas = when (chapterId) {
            "c10_m_ch1" -> listOf(
                KeyFormula("f1", "HCF-LCM Product Theorem", "HCF(a, b) × LCM(a, b) = a × b", "Valid only for two positive integers."),
                KeyFormula("f2", "Terminating Decimal Condition", "q = 2ⁿ · 5ᵐ", "Where n, m are non-negative integers for rational p/q in lowest terms.")
            )
            "c10_s_ch1" -> listOf(
                KeyFormula("f3", "Quicklime Slaking Reaction", "CaO(s) + H₂O(l) → Ca(OH)₂(aq) + Heat", "Highly exothermic combination reaction."),
                KeyFormula("f4", "Photosynthesis Reaction", "6CO₂ + 12H₂O + Sunlight → C₆H₁₂O₆ + 6O₂ + 6H₂O", "Standard photochemical reduction reaction.")
            )
            "c12_p_ch1" -> listOf(
                KeyFormula("f5", "Coulomb's Law", "F = (1 / 4πε₀) · (|q₁ q₂| / r²)", "In SI units, 1/(4πε₀) ≈ 9 × 10⁹ N·m²/C²."),
                KeyFormula("f6", "Gauss's Law Flux", "Φ_E = ∮ E · dA = Q_encl / ε₀", "Useful for highly symmetrical charge geometries.")
            )
            "c12_c_ch1" -> listOf(
                KeyFormula("f7", "Elevation in Boiling Point", "ΔT_b = i · K_b · m", "m is molality; K_b is ebullioscopic constant."),
                KeyFormula("f8", "Osmotic Pressure", "Π = i · C · R · T", "C is molar concentration in mol/L, R = 0.0821 L·atm/(mol·K).")
            )
            else -> listOf(
                KeyFormula("f_gen1", "Fundamental Relation", "Output = f(Input, Parameters)", "Standard definition used in this curriculum."),
                KeyFormula("f_gen2", "Conservation Balance", "Total_Initial = Total_Final", "Universal conservation law.")
            )
        }

        val sampleConcepts = listOf(
            ConceptPoint("cp1", "Core Examination Principle", "Frequently tested in Board exams. Requires clear definitions, units, and labelled diagrams.", "High Priority"),
            ConceptPoint("cp2", "Common Student Misconception", "Carefully distinguish between definitions and empirical approximations during problem solving.", "Exam Tip"),
            ConceptPoint("cp3", "Step-by-Step Problem Solving", "Always write given parameters first, state the governing formula, substitute with SI units, and box the final result.", "Best Practice")
        )

        val sampleQuiz = when (chapterId) {
            "c10_m_ch1" -> listOf(
                QuizQuestion(
                    id = "q1",
                    chapterId = chapterId,
                    questionText = "If HCF(306, 657) = 9, what is the LCM(306, 657)?",
                    options = listOf("22,338", "2,233", "20,114", "24,560"),
                    correctOptionIndex = 0,
                    explanation = "Using HCF × LCM = a × b: 9 × LCM = 306 × 657. Therefore, LCM = (306 × 657) / 9 = 34 × 657 = 22,338."
                ),
                QuizQuestion(
                    id = "q2",
                    chapterId = chapterId,
                    questionText = "Which of the following rational numbers has a non-terminating repeating decimal expansion?",
                    options = listOf("17 / 8", "13 / 125", "77 / 210", "15 / 1600"),
                    correctOptionIndex = 2,
                    explanation = "In 77/210, simplifying yields 11/30. The denominator 30 = 2 × 3 × 5 has a prime factor 3 (other than 2 or 5), hence non-terminating repeating."
                ),
                QuizQuestion(
                    id = "q3",
                    chapterId = chapterId,
                    questionText = "The total number of prime factors in 144 is:",
                    options = listOf("4", "6", "2", "8"),
                    correctOptionIndex = 1,
                    explanation = "144 = 2⁴ × 3². The sum of the exponents of prime factors is 4 + 2 = 6."
                )
            )
            "c12_p_ch1" -> listOf(
                QuizQuestion(
                    id = "q4",
                    chapterId = chapterId,
                    questionText = "What is the SI unit of electric flux Φ?",
                    options = listOf("N · m² / C", "N / C", "C / m²", "Volt / m²"),
                    correctOptionIndex = 0,
                    explanation = "Electric flux Φ = E · A. The unit of E is N/C and Area is m², giving N · m² / C (also equivalent to Volt · meter)."
                ),
                QuizQuestion(
                    id = "q5",
                    chapterId = chapterId,
                    questionText = "When a dielectric slab of constant K is inserted into a capacitor connected to a battery, the capacitance:",
                    options = listOf("Increases by K times", "Decreases by K times", "Remains unchanged", "Becomes zero"),
                    correctOptionIndex = 0,
                    explanation = "The presence of a dielectric increases the capacitance according to C = K · C₀."
                )
            )
            else -> listOf(
                QuizQuestion(
                    id = "q_gen1",
                    chapterId = chapterId,
                    questionText = "What is the primary governing principle of this chapter?",
                    options = listOf("Conservation of Energy and Mass", "Arbitrary Variation", "Static Equilibrium only", "None of the above"),
                    correctOptionIndex = 0,
                    explanation = "Most scientific and mathematical derivations in secondary curriculum derive from core conservation principles."
                ),
                QuizQuestion(
                    id = "q_gen2",
                    chapterId = chapterId,
                    questionText = "Which practice is recommended for high scoring in board examinations?",
                    options = listOf("Show detailed step-by-step reasoning", "Omit formula notation", "Guess numerical answers", "Skip checking units"),
                    correctOptionIndex = 0,
                    explanation = "Board marking schemes allocate dedicated marks for formulas, proper substitution, and SI units."
                )
            )
        }

        // The 7 canonical steps requested by the user in Step Learning
        val steps = listOf(
            StepLearningItem(1, "Learn the Basics", "Fundamental terminology, axioms, and intuitive background.", "Read the foundational definitions and historical context."),
            StepLearningItem(2, "Understand the Concept", "Deep dive into governing laws, logic, and physical intuition.", "Analyze the main theorems, derivations, and diagrams."),
            StepLearningItem(3, "Study Examples", "Walk through solved standard NCERT & Board problems.", "Follow the structured step-by-step working of key exemplars."),
            StepLearningItem(4, "Practice", "Solve targeted questions independently to build speed.", "Apply formulas to typical problem variations."),
            StepLearningItem(5, "Take a Mini Quiz", "Assess your understanding under timed conditions.", "Test recall, accuracy, and conceptual clarity."),
            StepLearningItem(6, "Review Mistakes", "Identify gaps, misconceptions, and weak spots.", "Read targeted explanations for any incorrect answers."),
            StepLearningItem(7, "Mark Chapter Complete", "Celebrate mastery and update your study streak!", "Solidify progress and unlock advanced revision tools.")
        )

        return ChapterFullContent(
            chapter = chapter,
            notes = sampleNotes,
            importantConcepts = sampleConcepts,
            formulas = sampleFormulas,
            quizQuestions = sampleQuiz,
            stepLearning = steps
        )
    }

    // Global Search across curriculum
    data class SearchResult(
        val title: String,
        val subtitle: String,
        val type: String, // "Subject", "Chapter", "Formula", "Quiz Question"
        val subjectId: String,
        val chapterId: String? = null
    )

    fun searchCurriculum(query: String): List<SearchResult> {
        if (query.isBlank()) return emptyList()
        val q = query.trim().lowercase()
        val results = mutableListOf<SearchResult>()

        // Search subjects
        subjects.filter { it.name.lowercase().contains(q) || it.description.lowercase().contains(q) }
            .forEach { subject ->
                results.add(SearchResult(subject.name, "${subject.grade.displayName} • ${subject.description}", "Subject", subject.id))
            }

        // Search chapters
        chapters.filter { it.title.lowercase().contains(q) || it.summary.lowercase().contains(q) }
            .forEach { chapter ->
                val subject = getSubjectById(chapter.subjectId)
                results.add(SearchResult(chapter.title, "${subject?.name ?: "Subject"} • Chapter ${chapter.chapterNumber}", "Chapter", chapter.subjectId, chapter.id))
            }

        // Search formulas
        listOf(
            SearchResult("HCF-LCM Product Theorem", "Real Numbers • HCF(a, b) × LCM(a, b) = a × b", "Formula", "c10_math", "c10_m_ch1"),
            SearchResult("Coulomb's Law", "Electric Charges • F = (1/4πε₀) · (|q₁q₂|/r²)", "Formula", "c12_phy", "c12_p_ch1"),
            SearchResult("Raoult's Law & Colligative Elevation", "Solutions • ΔT_b = i · K_b · m", "Formula", "c12_chem", "c12_c_ch1")
        ).filter { it.title.lowercase().contains(q) || it.subtitle.lowercase().contains(q) }
            .forEach { results.add(it) }

        return results
    }
}
