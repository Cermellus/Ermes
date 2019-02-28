/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { ErmesTestModule } from '../../../test.module';
import { ErmesUserDetailComponent } from 'app/entities/ermes-user/ermes-user-detail.component';
import { ErmesUser } from 'app/shared/model/ermes-user.model';

describe('Component Tests', () => {
    describe('ErmesUser Management Detail Component', () => {
        let comp: ErmesUserDetailComponent;
        let fixture: ComponentFixture<ErmesUserDetailComponent>;
        const route = ({ data: of({ ermesUser: new ErmesUser(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [ErmesTestModule],
                declarations: [ErmesUserDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ErmesUserDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ErmesUserDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.ermesUser).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
