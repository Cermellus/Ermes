/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { CreditReturnTypeDetailComponent } from 'app/entities/credit-return-type/credit-return-type-detail.component';
import { CreditReturnType } from 'app/shared/model/credit-return-type.model';

describe('Component Tests', () => {
    describe('CreditReturnType Management Detail Component', () => {
        let comp: CreditReturnTypeDetailComponent;
        let fixture: ComponentFixture<CreditReturnTypeDetailComponent>;
        const route = ({ data: of({ creditReturnType: new CreditReturnType(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [CreditReturnTypeDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CreditReturnTypeDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CreditReturnTypeDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.creditReturnType).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
